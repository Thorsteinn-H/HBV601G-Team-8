package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentRoadBinding
import `is`.hi.hbv601g.icelandicweatherapp.utilities.convertToLatLng
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import kotlin.getValue


//Fragment responsible for displaying road conditions
class RoadFragment : Fragment() {

    // View binding
    private var _binding: FragmentRoadBinding? = null
    private val binding get() = _binding!!

    //ViewModel that provides road data
    private val viewModel: RoadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoadBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load osmdroid configuration
        // required for map to work properly
        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )

        val map = binding.mapviewRoad

        // set map style
        map.setTileSource(TileSourceFactory.MAPNIK)

        //enable pinch zoom
        map.setMultiTouchControls(true)

        //initail zoom and center
        map.controller.setZoom(7.0)
        map.controller.setCenter(GeoPoint(64.9631, -19.0208))

        //load the roads
        viewModel.loadRoads()

        //observe livedata from ViewModel
        viewModel.roads.observe(viewLifecycleOwner){ data ->
            // clear previous drawing
            map.overlays.clear()

            //loop for each road
            data.forEach { road ->

                //each road can have multiple path segments
                road.paths.forEach { path ->


                    val polyline = Polyline()
                    val geoPoints = mutableListOf<GeoPoint>()

                    //convert each point from icelandic coordinates to gps
                    path.forEach { (x, y) ->
                        val(lat,lon) = convertToLatLng(x,y)
                        geoPoints.add(GeoPoint(lat, lon)) // (lat, lon)
                    }

                    //assign points to polyline
                    polyline.setPoints(geoPoints)

                    //parse color from API
                    val color = try {
                        Color.parseColor(road.colorHex ?: "#FF0000")
                    } catch (e: Exception) {
                        Color.RED
                    }

                    //apply styling
                    polyline.outlinePaint.color = color
                    polyline.outlinePaint.strokeWidth = 25f

                    //add line to map
                    map.overlays.add(polyline)

                }
            }
            //refresh map
            map.invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}