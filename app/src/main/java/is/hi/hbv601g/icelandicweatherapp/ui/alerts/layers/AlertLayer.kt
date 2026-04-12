package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.annotation.UiContext
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import `is`.hi.hbv601g.icelandicweatherapp.model.Alert
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.MapViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon

class AlertLayer() {
    private var alerts: List<Alert> = emptyList()

    fun load(viewModel: MapViewModel, lifecycleOwner: LifecycleOwner) {
        viewModel.loadAlerts()

        viewModel.alerts.observe(lifecycleOwner) {
            alerts = it
        }
    }

    fun draw(map: MapView) {

        map.overlays.clear()

        alerts.forEach { alert ->

            alert.polygons.forEach { polygonCoords ->

                val polygon = Polygon()

                val geoPoints = polygonCoords

                polygon.points = geoPoints

                val color = getColor(alert.severity)

                polygon.fillPaint.color = color
                polygon.outlinePaint.color = Color.BLACK
                polygon.outlinePaint.strokeWidth = 4f

                polygon.setOnClickListener{ _, mapView, _ ->

                    Toast.makeText(
                        mapView.context,
                        alert.descriptionEn,
                        Toast.LENGTH_LONG
                    ).show()

                    true
                }
                map.overlays.add(polygon)
            }
        }

        map.invalidate()
    }

    private fun getColor(severity: String): Int {
        return when (severity.lowercase()) {
            "severe" -> Color.parseColor("#55FF0000")
            "moderate" -> Color.parseColor("#55FFA500")
            "minor" -> Color.parseColor("#55FFFF00")
            else -> Color.parseColor("#55999999")
        }
    }
}