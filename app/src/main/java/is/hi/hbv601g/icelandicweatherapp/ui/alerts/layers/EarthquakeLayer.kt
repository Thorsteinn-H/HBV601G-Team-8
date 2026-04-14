package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.MapViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.time.OffsetDateTime

class EarthquakeLayer(private val context: Context) {
    private val earthquakeMarkers = mutableListOf<Marker>()

    // 🔹 Load data (called once)
    fun load(viewModel: MapViewModel, lifecycleOwner: LifecycleOwner, map: MapView) {

        val start = OffsetDateTime.now().minusDays(1).toString()

        viewModel.loadEarthquakes(start)

        viewModel.earthquake.observe(lifecycleOwner) { data ->

            earthquakeMarkers.clear() //prevent duplicates

            val features = data.features

            features.forEach { quake ->

                val coords = quake.geometry.coordinates
                val magnitude = quake.properties.magnitude

                if (magnitude < 1) return@forEach

                val depth = quake.properties.depth
                val lng = coords[0]
                val lat = coords[1]

                val marker = Marker(map)

                setIcon(marker, magnitude)

                marker.position = GeoPoint(lat, lng)
                marker.title = quake.properties.region
                marker.subDescription =
                    "Skjálfti af stærð: $magnitude í dýpt: $depth km"

                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                earthquakeMarkers.add(marker)
            }
        }
    }

    // 🔹 Draw on map (called on button click)
    fun draw(map: MapView) {
        map.overlays.clear()
        earthquakeMarkers.forEach { map.overlays.add(it) }
        map.invalidate()
    }

    // 🔹 Icon logic
    private fun setIcon(marker: Marker, magnitude: Double) {

        val drawable = when {
            magnitude > 3 -> ContextCompat.getDrawable(context, R.drawable.redpin)
            magnitude in 2.0..3.0 -> ContextCompat.getDrawable(context, R.drawable.pin_yellow)
            else -> ContextCompat.getDrawable(context, R.drawable.pin_green)
        }

        marker.icon = drawable
    }
}