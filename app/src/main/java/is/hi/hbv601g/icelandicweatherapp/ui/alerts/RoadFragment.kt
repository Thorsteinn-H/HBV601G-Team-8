package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentRoadBinding

class RoadFragment : Fragment() {

    private var _binding: FragmentRoadBinding? = null
    private val binding get() = _binding!!

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

        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )

        val map = binding.mapviewRoad

        map.setTileSource(TileSorceFactory.MAPNIK)
        map.setMuliTouchControls(true)

        map.controller.setZoom(7.0)
        map.controller.setCenter(GeoPoint(64.9631, -19.0208))

        viewModel.loadRoads()

        viewModel.roads.observe(viewLifecycleOwner){ data ->

            map.overlays.clear()

            data.forEach { road ->
                val latitude = road.lat
                val longitude = road.lon

                if(latitude == null || longitude == null) return@forEach

                val marker = Marker(map)

                val surface = road.surface ?: ""

                when(surface){
                    "FÆRT" -> marker.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.pin_green)

                    "HÁLKA" -> marker.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.pin_yellow)

                    "ÞUNGFÆRT" -> marker.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.pin_orange)

                    else -> marker.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.redpin)
                }

                marker.position = Geopoint(lat, lon)
                marker.title = road.roadName ?: "Unkown road"
                marker.subDescription = road.description ?: ""

                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                map.overlays.add(marker)
            }

            map.invalidtate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}