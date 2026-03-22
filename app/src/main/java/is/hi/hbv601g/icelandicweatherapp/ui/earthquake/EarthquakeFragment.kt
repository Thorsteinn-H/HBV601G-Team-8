package `is`.hi.hbv601g.icelandicweatherapp.ui.earthquake

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentEarthquakesBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import java.time.OffsetDateTime


class EarthquakeFragment: Fragment()  {

    private var _binding: FragmentEarthquakesBinding? = null

    private val binding get() = _binding!!

    private val viewModel: EarthquakeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthquakesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        val map = binding.mapviewEarhquake
        map.minZoomLevel=7.0
        map.maxZoomLevel=12.0
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setCenter(GeoPoint(64.9631,-19.0208))

        val start = OffsetDateTime.now().minusDays(1).toString()

        viewModel.loadEarthquakes(start)

        viewModel.earthquake.observe(viewLifecycleOwner) { data ->
                val features = data.features
                features.forEach { quake ->
                    val cords = quake.geometry.coordinates
                    val magnitude = quake.properties.magnitude
                    if(magnitude<1){return@forEach}
                    val depth = quake.properties.depth
                    val lng = cords[0]
                    val lat = cords[1]

                    val marker = Marker(map)

                    if(magnitude in 2.0..3.0){

                        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_yellow)
                        marker.icon = drawable


                    }else if (magnitude>3){
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.redpin)
                    marker.icon = drawable

                    } else{
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_green)
                    marker.icon = drawable

                    }

                    marker.setPosition(GeoPoint(lat, lng))
                    marker.title = quake.properties.region
                    marker.subDescription="Skjálfti af stærð " + magnitude.toString()+" í dýpt "+depth.toString()

                    marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM)

                    map.overlays.add(marker);

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}