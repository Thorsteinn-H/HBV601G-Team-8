package `is`.hi.hbv601g.icelandicweatherapp.ui.northernlights

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import `is`.hi.hbv601g.icelandicweatherapp.R
import android.widget.Toast
import `is`.hi.hbv601g.icelandicweatherapp.data.KpForecastEntry
import `is`.hi.hbv601g.icelandicweatherapp.data.Kp27DayOutlookEntry
import `is`.hi.hbv601g.icelandicweatherapp.data.NorthernLightsCurrentDto
import `is`.hi.hbv601g.icelandicweatherapp.data.KpIndexEntry
import java.util.Locale
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.FolderOverlay
import java.text.SimpleDateFormat
import java.util.*

class NorthernLightsFragment : Fragment(R.layout.fragment_northern_lights) {

    private lateinit var viewModel: NorthernLightsViewModel
    private lateinit var map: MapView
    private lateinit var kpValue: TextView
    private lateinit var windValue: TextView
    private lateinit var imfValue: TextView
    private lateinit var likelihoodStatus: TextView
    private lateinit var timeSlider: SeekBar
    private lateinit var timeText: TextView
    private lateinit var btnToggleClouds: Button
    private lateinit var btnToggleLightPollution: Button
    private lateinit var cloudLegend: LinearLayout
    private lateinit var kpForecastContainer: LinearLayout
    private lateinit var kp27DayContainer: LinearLayout

    private val auroraOverlay = FolderOverlay()
    private val cloudOverlay = FolderOverlay()
    private val lightOverlay = FolderOverlay()

    private var cloudMode = 1 // 0: All, 1: Differentiated (Default to Detailed as it's more reliable)
    private var showLightPollution = false
    private var currentForecastHour = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize OSMDroid
        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )

        viewModel = ViewModelProvider(this)[NorthernLightsViewModel::class.java]

        // Bind Views
        map = view.findViewById(R.id.mapNorthernLights)
        kpValue = view.findViewById(R.id.kpValue)
        windValue = view.findViewById(R.id.windValue)
        imfValue = view.findViewById(R.id.imfValue)
        likelihoodStatus = view.findViewById(R.id.likelihoodStatus)
        timeSlider = view.findViewById(R.id.timeSlider)
        timeText = view.findViewById(R.id.timeText)
        btnToggleClouds = view.findViewById(R.id.btnToggleClouds)
        btnToggleLightPollution = view.findViewById(R.id.btnToggleLightPollution)
        cloudLegend = view.findViewById(R.id.cloudLegend)
        kpForecastContainer = view.findViewById(R.id.kpForecastContainer)
        kp27DayContainer = view.findViewById(R.id.kp27DayContainer)

        setupMap()
        setupListeners()
        observeViewModel()

        // Set initial UI state for default cloudMode = 1
        btnToggleClouds.text = "Ský: Nákvæmt"
        cloudLegend.visibility = View.VISIBLE

        viewModel.loadAllData()
        updateMapLayers() // Initial map layers
    }

    private fun setupMap() {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.minZoomLevel = 5.0
        map.maxZoomLevel = 10.0
        map.controller.setCenter(GeoPoint(64.9631, -19.0208)) // Center of Iceland
        map.controller.setZoom(6.5)

        // Add overlays in order (Bottom to Top: Map -> Light -> Clouds -> Aurora)
        map.overlays.add(lightOverlay)
        map.overlays.add(cloudOverlay)
        map.overlays.add(auroraOverlay)
    }

    private fun setupListeners() {
        btnToggleClouds.setOnClickListener {
            cloudMode = (cloudMode + 1) % 2
            btnToggleClouds.text = if (cloudMode == 0) "Ský: Öll" else "Ský: Nákvæmt"
            cloudLegend.visibility = if (cloudMode == 1) View.VISIBLE else View.GONE
            updateMapLayers()
        }

        btnToggleLightPollution.setOnClickListener {
            showLightPollution = !showLightPollution
            btnToggleLightPollution.alpha = if (showLightPollution) 1.0f else 0.5f
            updateMapLayers()
        }

        timeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            private var lastUpdateTime = 0L
            private val UPDATE_INTERVAL = 300L // ms

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentForecastHour = progress
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.HOUR_OF_DAY, progress)
                
                val sdf = java.text.SimpleDateFormat("HH:mm", Locale.US)
                val timeStr = sdf.format(calendar.time)
                
                timeText.text = if (progress == 0) "Núna" else "+$progress klst: $timeStr"
                
                // Update top stats based on forecast
                updateTopStatsForHour(progress)
                
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastUpdateTime > UPDATE_INTERVAL) {
                    updateMapLayers()
                    lastUpdateTime = currentTime
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updateMapLayers() // Ensure final state is drawn
            }
        })
    }

    private fun observeViewModel() {
        viewModel.kpIndexEntries.observe(viewLifecycleOwner) { entries ->
            if (entries.isNotEmpty()) {
                val currentKp = entries.last().kp
                kpValue.text = String.format(Locale.US, "%.2f", currentKp)
                updateLikelihood(currentKp)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                android.widget.Toast.makeText(requireContext(), it, android.widget.Toast.LENGTH_LONG).show()
            }
        }

        viewModel.solarWind.observe(viewLifecycleOwner) { data ->
            if (data.size > 1) {
                val lastRow = data.last()
                if (lastRow.size > 2) {
                    windValue.text = "${lastRow[2]} km/s"
                }
            }
        }

        viewModel.imf.observe(viewLifecycleOwner) { data ->
            if (data.size > 1) {
                val lastRow = data.last()
                if (lastRow.size > 6) {
                    imfValue.text = "${lastRow[3]} nT" // Bz is usually index 3 in mag-5-min
                }
            }
        }

        viewModel.currentNorthernLights.observe(viewLifecycleOwner) { data ->
            drawAuroraOverlay(data.coordinates)
        }

        viewModel.kpForecastEntries.observe(viewLifecycleOwner) { entries ->
            val now = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
            val utc = TimeZone.getTimeZone("UTC")
            val inputSdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply { timeZone = utc }
            val fallbackSdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).apply { timeZone = utc }
            
            val filtered = entries.filter { 
                val time = try {
                    inputSdf.parse(it.timeTag)?.time ?: fallbackSdf.parse(it.timeTag)?.time ?: 0L
                } catch (e: Exception) { 0L }
                // Only show entries that are current or future (within 3 hours of now)
                time > now - 3 * 3600 * 1000 
            }
            renderKpForecastFromEntries(filtered, kpForecastContainer)
        }

        viewModel.kp27DayEntries.observe(viewLifecycleOwner) { entries ->
            val now = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
            val inputSdf = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") }
            
            val filtered = entries.filter {
                val time = try { inputSdf.parse(it.date)?.time ?: 0L } catch(e: Exception) { 0L }
                // Show from today onwards
                time >= now - 24 * 3600 * 1000
            }
            renderKp27DayForecast(filtered, kp27DayContainer)
        }

        viewModel.cloudForecast.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                updateMapLayers()
            }
        }

        viewModel.detailedClouds.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                updateMapLayers()
            }
        }
    }

    private fun updateTopStatsForHour(hourOffset: Int) {
        if (hourOffset == 0) {
            // Reset to current real-time values
            viewModel.kpIndexEntries.value?.lastOrNull()?.let {
                kpValue.text = String.format(Locale.US, "%.1f", it.kp)
                updateLikelihood(it.kp)
            }
            viewModel.solarWind.value?.lastOrNull()?.let {
                if (it.size > 2) windValue.text = "${it[2]} km/s"
            }
            viewModel.imf.value?.lastOrNull()?.let {
                if (it.size > 3) imfValue.text = "${it[3]} nT"
            }
            windValue.alpha = 1.0f
            imfValue.alpha = 1.0f
            highlightForecastBar(-1)
            return
        }

        val forecastEntries = viewModel.kpForecastEntries.value ?: return
        if (forecastEntries.isEmpty()) return

        val targetTime = Calendar.getInstance()
        targetTime.add(Calendar.HOUR_OF_DAY, hourOffset)
        
        // Handle ISO 8601 with T: 2026-04-15T18:00:00
        val inputSdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        
        var closestEntry: KpForecastEntry? = null
        var minDiff = Long.MAX_VALUE
        var closestIndex = -1

        forecastEntries.forEachIndexed { index, entry ->
            try {
                // Try both formats just in case
                val entryTime = try {
                    inputSdf.parse(entry.timeTag)?.time
                } catch (e: Exception) {
                    java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(entry.timeTag)?.time
                } ?: 0L
                
                val diff = Math.abs(entryTime - targetTime.timeInMillis)
                if (diff < minDiff) {
                    minDiff = diff
                    closestEntry = entry
                    closestIndex = index
                }
            } catch (e: Exception) {}
        }

        closestEntry?.let {
            kpValue.text = String.format(Locale.US, "%.1f", it.kp)
            updateLikelihood(it.kp)
            windValue.alpha = 0.5f
            imfValue.alpha = 0.5f
            highlightForecastBar(closestIndex)
        }
    }

    private fun highlightForecastBar(index: Int) {
        for (i in 0 until kpForecastContainer.childCount) {
            val wrapper = kpForecastContainer.getChildAt(i) as? LinearLayout ?: continue
            if (i == index) {
                wrapper.setBackgroundColor(Color.argb(60, 0, 150, 255)) // Highlight Blue
                wrapper.scaleX = 1.1f
                wrapper.scaleY = 1.1f
            } else {
                wrapper.setBackgroundColor(Color.TRANSPARENT)
                wrapper.scaleX = 1.0f
                wrapper.scaleY = 1.0f
            }
        }
    }

    private fun renderKpForecastFromEntries(entries: List<KpForecastEntry>, container: LinearLayout) {
        container.removeAllViews()
        
        // Handle ISO 8601 with T: 2026-04-15T18:00:00
        val inputSdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val fallbackSdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val displaySdf = java.text.SimpleDateFormat("HH:mm\ndd/MM", Locale.US)

        for (entry in entries) {
            val kp = entry.kp
            
            val bar = View(requireContext())
            val params = LinearLayout.LayoutParams(90, (kp * 30).toInt().coerceAtLeast(20))
            params.setMargins(12, 0, 12, 0)
            bar.layoutParams = params
            bar.setBackgroundColor(getKpColor(kp))
            
            val wrapper = LinearLayout(requireContext())
            wrapper.orientation = LinearLayout.VERTICAL
            wrapper.gravity = android.view.Gravity.BOTTOM or android.view.Gravity.CENTER_HORIZONTAL
            wrapper.setPadding(4, 8, 4, 8)
            
            val label = TextView(requireContext())
            label.text = String.format(Locale.US, "%.0f", kp)
            label.textSize = 12f
            label.setTextColor(Color.BLACK)
            label.setTypeface(null, android.graphics.Typeface.BOLD)
            label.gravity = android.view.Gravity.CENTER
            
            val timeLabel = TextView(requireContext())
            timeLabel.textSize = 9f
            timeLabel.setLineSpacing(0f, 0.8f)
            timeLabel.setTextColor(Color.rgb(50, 50, 50)) // Very Dark Gray
            timeLabel.gravity = android.view.Gravity.CENTER
            timeLabel.setPadding(0, 4, 0, 0)
            
            try {
                val date = try {
                    inputSdf.parse(entry.timeTag)
                } catch (e: Exception) {
                    fallbackSdf.parse(entry.timeTag)
                }
                if (date != null) {
                    timeLabel.text = displaySdf.format(date)
                }
            } catch (e: Exception) {
                timeLabel.text = "??:??"
            }
            
            wrapper.addView(label)
            wrapper.addView(bar)
            wrapper.addView(timeLabel)
            container.addView(wrapper)
        }
    }

    private fun renderKp27DayForecast(entries: List<Kp27DayOutlookEntry>, container: LinearLayout) {
        container.removeAllViews()
        val inputSdf = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val dateSdf = java.text.SimpleDateFormat("dd/MM", Locale.US)

        for (entry in entries) {
            val kp = entry.kp.toDouble()
            
            val bar = View(requireContext())
            val params = LinearLayout.LayoutParams(90, (kp * 30).toInt().coerceAtLeast(20))
            params.setMargins(12, 0, 12, 0)
            bar.layoutParams = params
            bar.setBackgroundColor(getKpColor(kp))
            
            val wrapper = LinearLayout(requireContext())
            wrapper.orientation = LinearLayout.VERTICAL
            wrapper.gravity = android.view.Gravity.BOTTOM or android.view.Gravity.CENTER_HORIZONTAL
            wrapper.setPadding(4, 8, 4, 8)
            
            val label = TextView(requireContext())
            label.text = String.format(Locale.US, "%.0f", kp)
            label.textSize = 12f
            label.setTextColor(Color.BLACK)
            label.setTypeface(null, android.graphics.Typeface.BOLD)
            label.gravity = android.view.Gravity.CENTER
            
            val timeLabel = TextView(requireContext())
            timeLabel.textSize = 10f
            timeLabel.setTextColor(Color.rgb(50, 50, 50)) // Very Dark Gray
            timeLabel.gravity = android.view.Gravity.CENTER
            timeLabel.setPadding(0, 4, 0, 0)
            
            try {
                val date = inputSdf.parse(entry.date)
                if (date != null) {
                    timeLabel.text = dateSdf.format(date)
                }
            } catch (e: Exception) {
                timeLabel.text = ""
            }
            
            wrapper.addView(label)
            wrapper.addView(bar)
            wrapper.addView(timeLabel)
            container.addView(wrapper)
        }
    }

    private fun getKpColor(kp: Double): Int {
        return when {
            kp < 2 -> Color.GREEN
            kp < 4 -> Color.YELLOW
            kp < 6 -> Color.rgb(255, 165, 0) // Orange
            else -> Color.RED
        }
    }

    private fun updateLikelihood(kp: Double) {
        val status = when {
            kp < 2 -> "Lítil"
            kp < 4 -> "Miðlungs"
            kp < 6 -> "Mikil"
            else -> "Mjög mikil"
        }
        val color = when {
            kp < 2 -> Color.RED
            kp < 4 -> Color.YELLOW
            kp < 6 -> Color.GREEN
            else -> Color.MAGENTA
        }
        likelihoodStatus.text = "Sýnileika líkur: $status"
        likelihoodStatus.setTextColor(color)
    }

    private fun drawAuroraOverlay(coordinates: List<List<Double>>) {
        auroraOverlay.items.clear()
        
        // Subsample for performance: take every 5th point
        val pointsNearIceland = coordinates.filterIndexed { index, _ -> index % 5 == 0 }.filter { 
            val rawLon = it[0]
            val lon = if (rawLon > 180) rawLon - 360 else rawLon
            val lat = it[1]
            val prob = it[2]
            lat in 62.0..68.0 && lon in -26.0..-12.0 && prob > 0
        }

        pointsNearIceland.forEach { point ->
            val rawLon = point[0]
            val lon = if (rawLon > 180) rawLon - 360 else rawLon
            val lat = point[1]
            val prob = point[2]

            val circle = Polygon(map)
            val center = GeoPoint(lat, lon)
            circle.points = Polygon.pointsAsCircle(center, 40000.0)
            
            val alpha = (prob * 2.0).toInt().coerceIn(0, 180) 
            circle.fillPaint.color = Color.argb(alpha, 0, 255, 0)
            circle.outlinePaint.color = Color.TRANSPARENT
            circle.outlinePaint.strokeWidth = 0f
            auroraOverlay.items.add(circle)
        }
        map.invalidate()
    }

    private fun updateMapLayers() {
        cloudOverlay.items.clear()
        lightOverlay.items.clear()

        drawCloudLayer()

        if (showLightPollution) {
            drawLightPollutionLayer()
        }
        
        map.invalidate()
    }

    private fun getForecastHourIndex(times: List<String>?): Int {
        if (times == null || times.isEmpty()) return 0
        val now = Calendar.getInstance()
        val currentHour = now.get(Calendar.HOUR_OF_DAY)
        return (currentHour + currentForecastHour).coerceIn(0, times.size - 1)
    }

    private fun drawCloudLayer() {
        if (cloudMode == 0) {
            // Use OpenWeatherMap for "All Clouds" (Simple)
            val forecast = viewModel.cloudForecast.value
            // OpenWeatherMap is 3-hour steps. 
            val idx = (currentForecastHour / 3).coerceIn(0, (forecast?.list?.size ?: 1) - 1)
            val cloudPercent = forecast?.list?.getOrNull(idx)?.clouds?.all ?: 0
            
            if (cloudPercent < 5) return
            drawCloudBlobs(cloudPercent, Color.parseColor("#B0BEC5"), 123)
        } else {
            // Use Open-Meteo for "Detailed Clouds" (Low, Mid, High)
            val detailed = viewModel.detailedClouds.value?.hourly
            val hourIdx = getForecastHourIndex(detailed?.time)
            
            val low = detailed?.cloud_cover_low?.getOrNull(hourIdx) ?: 0
            val mid = detailed?.cloud_cover_mid?.getOrNull(hourIdx) ?: 0
            val high = detailed?.cloud_cover_high?.getOrNull(hourIdx) ?: 0

            // Draw each layer if it exists
            if (low > 5) drawCloudBlobs(low, Color.parseColor("#3949AB"), 456)
            if (mid > 5) drawCloudBlobs(mid, Color.parseColor("#880E4F"), 789)
            if (high > 5) drawCloudBlobs(high, Color.parseColor("#26A69A"), 321)
        }
    }

    private fun drawCloudBlobs(percent: Int, baseColor: Int, seed: Long) {
        val random = java.util.Random(seed + currentForecastHour)
        // More blobs for better coverage
        val blobCount = (percent / 8).coerceAtLeast(5)

        repeat(blobCount) {
            val centerLat = 63.3 + (random.nextDouble() * 3.2)
            val centerLon = -24.5 + (random.nextDouble() * 11.0)

            val poly = Polygon(map)
            val points = mutableListOf<GeoPoint>()
            val segments = 10
            // Larger radius for better visibility
            val radius = 0.6 + (random.nextDouble() * 1.8) * (percent / 100.0)

            for (i in 0 until segments) {
                val angle = 2.0 * Math.PI * i / segments
                val r = radius * (0.7 + random.nextDouble() * 0.6)
                points.add(
                    GeoPoint(
                        centerLat + r * Math.cos(angle) * 0.5, // Mercator squeeze
                        centerLon + r * Math.sin(angle)
                    )
                )
            }
            points.add(points[0]) // Close the polygon

            poly.points = points
            poly.fillPaint.color = Color.argb(140, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor))
            poly.outlinePaint.color = Color.TRANSPARENT
            poly.outlinePaint.strokeWidth = 0f

            cloudOverlay.items.add(poly)
        }
    }

    private fun drawLightPollutionLayer() {
        val towns = listOf(
            GeoPoint(64.1466, -21.9426), // Reykjavík
            GeoPoint(65.6885, -18.1262), // Akureyri
            GeoPoint(65.2669, -14.3948), // Egilsstaðir
            GeoPoint(64.2591, -15.2031)  // Höfn
        )
        
        towns.forEach { coord ->
            val cityLight = Polygon(map)
            cityLight.points = Polygon.pointsAsCircle(coord, 25000.0)
            cityLight.fillPaint.color = Color.argb(100, 255, 50, 50)
            cityLight.outlinePaint.color = Color.TRANSPARENT
            cityLight.outlinePaint.strokeWidth = 0f
            lightOverlay.items.add(cityLight)
        }
    }
}