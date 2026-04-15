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
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers.AlertLayer
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers.EarthquakeLayer
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers.RoadLayer
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers.VolcanoLayer
import `is`.hi.hbv601g.icelandicweatherapp.utilities.convertToLatLng
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.time.OffsetDateTime

class MapFragment: Fragment() {

    private lateinit var volcanoLayer: VolcanoLayer
    private lateinit var earthquakeLayer: EarthquakeLayer

    private val alertLayer = AlertLayer()


   private val roadLayer = RoadLayer()

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

        earthquakeLayer = EarthquakeLayer(requireContext())

        volcanoLayer = VolcanoLayer(requireContext())


        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )

        val map = binding.mapviewmenu
        val earthQuakeButton = binding.buttonearthquake
        val volcanoButton = binding.buttonvolcano
        val roadsButton = binding.buttonroads
        val alertButton = binding.buttonweatheralerts

        map.minZoomLevel = 7.0
        map.maxZoomLevel = 12.0
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setCenter(GeoPoint(64.9631, -19.0208))

        earthquakeLayer.load(viewModel, viewLifecycleOwner, map)
        volcanoLayer.load(viewModel,viewLifecycleOwner,map)
        roadLayer.load(viewModel, viewLifecycleOwner)
        alertLayer.load(viewModel, viewLifecycleOwner)

        earthQuakeButton.setOnClickListener {
            earthquakeLayer.draw(map)
            binding.mapId.text="Jarðskjálftakort"
            binding.roadLegend.visibility = View.GONE
        }

        volcanoButton.setOnClickListener {
            volcanoLayer.draw(map)
            binding.mapId.text="Eldfjallakort"
            binding.roadLegend.visibility = View.GONE
        }

        roadsButton.setOnClickListener {
            roadLayer.draw(map)
            binding.mapId.text="VegaKort"
            binding.roadLegend.visibility = View.VISIBLE
        }

        alertButton.setOnClickListener {
            alertLayer.draw(map)
            binding.mapId.text="Veður viðvarana kort"
            binding.roadLegend.visibility = View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}