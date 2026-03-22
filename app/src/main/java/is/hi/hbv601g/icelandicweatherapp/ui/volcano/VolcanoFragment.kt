package `is`.hi.hbv601g.icelandicweatherapp.ui.volcano

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentVolcanosBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import kotlin.getValue

class VolcanoFragment: Fragment() {

    private var _binding: FragmentVolcanosBinding? = null

    private val binding get() = _binding!!

    private val viewModel: VolcanoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVolcanosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        val map = binding.mapviewVolcano
        map.minZoomLevel=7.0
        map.maxZoomLevel=12.0
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setCenter(GeoPoint(64.9631,-19.0208))

        viewModel.loadVolcanos()

        viewModel.volcano.observe(viewLifecycleOwner) { data ->
            data.forEach { volcano ->
                val name= volcano.volcano_name
                val lat = volcano.latitude
                val lng = volcano.longitude
                val status = volcano.status
                val timaset = volcano.publication_date //gera kannski "eftirlit hófst"

                var virkni="";

                val marker = Marker(map)

                if (status=="green"){
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_green)
                    marker.icon = drawable
                    virkni="engin"

                } else if(status=="yellow"){
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_yellow)
                    marker.icon = drawable
                    virkni="lítill"

                } else if(status=="orange"){
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.pin_orange)
                    marker.icon = drawable
                    virkni="miðlungs"

                } else{
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.redpin)
                    marker.icon = drawable
                    virkni="mikill"
                }


                marker.setPosition(GeoPoint(lat, lng))
                marker.title = name
                marker.subDescription="Eldgos sem er "  + virkni + " virkni."

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