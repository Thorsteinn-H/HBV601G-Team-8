package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.MapViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import `is`.hi.hbv601g.icelandicweatherapp.R
import androidx.core.content.ContextCompat
class VolcanoLayer(private val context: Context) {

    private val volcanoMarkers = mutableListOf<Marker>()

    // 🔹 Load data (called once)
    fun load(viewModel: MapViewModel, lifecycleOwner: LifecycleOwner, map: MapView) {

        viewModel.loadVolcanos()

        viewModel.volcano.observe(lifecycleOwner) { data ->

            volcanoMarkers.clear() // prevent duplicates

            data.forEach { volcano ->

                val marker = Marker(map)

                setIcon(marker, volcano.status)

                marker.position = GeoPoint(volcano.latitude, volcano.longitude)
                marker.title = volcano.volcano_name

                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                volcanoMarkers.add(marker)
            }
        }
    }

    // 🔹 Draw on map (called on button click)
    fun draw(map: MapView) {
        map.overlays.clear()
        volcanoMarkers.forEach { map.overlays.add(it) }
        map.invalidate()
    }

    // 🔹 Icon + description logic
    private fun setIcon(marker: Marker, status: String) {

        val (drawable, description) = when (status) {
            "green" -> Pair(
                ContextCompat.getDrawable(context, R.drawable.pin_green),
                "Eldgos sem er engin virkni."
            )

            "yellow" -> Pair(
                ContextCompat.getDrawable(context, R.drawable.pin_yellow),
                "Eldgos sem er lítil virkni."
            )

            "orange" -> Pair(
                ContextCompat.getDrawable(context, R.drawable.pin_orange),
                "Eldgos sem er miðlungs virkni."
            )

            else -> Pair(
                ContextCompat.getDrawable(context, R.drawable.redpin),
                "Eldgos sem er mikil virkni."
            )
        }

        marker.icon = drawable
        marker.subDescription = description
    }
}