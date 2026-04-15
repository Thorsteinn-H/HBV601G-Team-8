package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentMapMenuBinding
import `is`.hi.hbv601g.icelandicweatherapp.model.RoadCondition
import `is`.hi.hbv601g.icelandicweatherapp.utilities.convertToLatLng
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.time.OffsetDateTime

class MapFragment: Fragment() {

    private val volcanoMarkers = mutableListOf<Marker>()
    private val earthquakeMarkers = mutableListOf<Marker>()

    private var roadLines: List<RoadCondition> = emptyList()

    private var _binding: FragmentMapMenuBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )

        val map = binding.mapviewmenu
        val earthQuakeButton = binding.buttonearthquake
        val volcanoButton = binding.buttonvolcano
        val roadsButton = binding.buttonroads

        map.minZoomLevel = 7.0
        map.maxZoomLevel = 12.0
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setCenter(GeoPoint(64.9631, -19.0208))

        createEarthQuakeMarker(map)
        createVolcanoMarker(map)
        createRoadLines()

        earthQuakeButton.setOnClickListener {
            drawMarkers(earthquakeMarkers,map)
            binding.mapId.text="Jarðskjálftakort"
        }

        volcanoButton.setOnClickListener {
            drawMarkers(volcanoMarkers,map)
            binding.mapId.text="Eldfjallakort"
        }

        roadsButton.setOnClickListener {
            drawRoads(map)
            binding.mapId.text="VegaKort"
        }

        //weather alerts TODO

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun drawMarkers(markers: MutableList<Marker>, map: MapView) {
        map.overlays.clear()
        markers.forEach { marker -> map.overlays.add(marker); }
        map.invalidate()

    }

    fun earthQuakeIcon(marker: Marker, magnitude: Double) {

        if (magnitude in 2.0..3.0) {

            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_yellow)
            marker.icon = drawable


        } else if (magnitude > 3) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.redpin)
            marker.icon = drawable

        } else {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_green)
            marker.icon = drawable

        }
    }

    fun createEarthQuakeMarker(map: MapView) {
        val start = OffsetDateTime.now().minusDays(1).toString()

        viewModel.loadEarthquakes(start)

        viewModel.earthquake.observe(viewLifecycleOwner) { data ->
            data?.features?.forEach { quake ->
                val cords = quake.geometry.coordinates
                val magnitude = quake.properties.magnitude
                if (magnitude < 1) {
                    return@forEach
                }
                val depth = quake.properties.depth
                val lng = cords[0]
                val lat = cords[1]

                val marker = Marker(map)

                earthQuakeIcon(marker, magnitude)

                marker.setPosition(GeoPoint(lat, lng))
                marker.title = quake.properties.region
                marker.subDescription =
                    "Skjálfti af stærð " + magnitude.toString() + " í dýpt " + depth.toString()

                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                earthquakeMarkers.add(marker);

            }

        }
    }


    fun volcanoIcon(status: String, marker: Marker) {
        if (status == "green") {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_green)
            marker.icon = drawable
            marker.subDescription = "Eldgos sem er engin virkni."

        } else if (status == "yellow") {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_yellow)
            marker.icon = drawable
            marker.subDescription = "Eldgos sem er lítill virkni."

        } else if (status == "orange") {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_orange)
            marker.icon = drawable
            marker.subDescription = "Eldgos sem er miðlungs virkni."

        } else {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.redpin)
            marker.icon = drawable
            marker.subDescription = "Eldgos sem er mikill virkni."
        }
    }

    fun createVolcanoMarker(map: MapView) {

        viewModel.loadVolcanos()

        viewModel.volcano.observe(viewLifecycleOwner) { data ->
            data?.forEach { volcano ->
                val name = volcano.volcano_name
                val lat = volcano.latitude
                val lng = volcano.longitude
                val status = volcano.status
                val timaset = volcano.publication_date //gera kannski "eftirlit hófst"

                val marker = Marker(map)

                volcanoIcon(status,marker)

                marker.setPosition(GeoPoint(lat, lng))
                marker.title = name

                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                volcanoMarkers.add(marker);

            }
        }
    }

    fun createRoadLines() {
        viewModel.loadRoads()

        viewModel.roads.observe(viewLifecycleOwner) { data ->
            roadLines = data
        }
    }

    fun drawRoads(map: MapView) {
        map.overlays.clear()

        roadLines.forEach { road ->
            road.paths.forEach { path ->
                val polyline = Polyline()
                val geoPoints = mutableListOf<GeoPoint>()
                path.forEach { (x,y) ->
                    val (lat, lon) = convertToLatLng(x,y)
                    geoPoints.add(GeoPoint(lat,lon))
                }

                polyline.setPoints(geoPoints)

                val color = try{
                    Color.parseColor(road.colorHex ?: "#FF0000")
                } catch (e: Exception) {
                    Color.RED
                }

                polyline.outlinePaint.color = color
                polyline.outlinePaint.strokeWidth = 20F

                map.overlays.add(polyline)
            }

        }

        map.invalidate()
    }
}