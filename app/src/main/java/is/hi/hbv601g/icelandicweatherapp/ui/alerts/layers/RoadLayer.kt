package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.layers

import android.graphics.Color
import androidx.lifecycle.LifecycleOwner
import `is`.hi.hbv601g.icelandicweatherapp.model.RoadCondition
import `is`.hi.hbv601g.icelandicweatherapp.ui.alerts.MapViewModel
import `is`.hi.hbv601g.icelandicweatherapp.utilities.convertToLatLng
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

class RoadLayer {

    private var roadLines: List<Pair<List<GeoPoint>, Int>> = emptyList()

    fun load(viewModel: MapViewModel, lifecycleOwner: LifecycleOwner){
        viewModel.loadRoads()

        viewModel.roads.observe(lifecycleOwner){ data ->
            roadLines = data.flatMap { road ->

                val color = try {
                    Color.parseColor(road.colorHex ?: "#FF0000")
                } catch (e: Exception) {
                    Color.RED
                }

                road.paths.map { path ->

                    val geoPoints = path.map { (x, y) ->
                        val (lat, lon) = convertToLatLng(x, y)
                        GeoPoint(lat, lon)
                    }

                    geoPoints to color
                }
            }

        }
    }
    fun draw(map: MapView) {
        map.overlays.clear()

        roadLines.forEach { (points, color) ->

            val polyline = Polyline()
            polyline.setPoints(points)

            polyline.outlinePaint.color = color
            polyline.outlinePaint.strokeWidth = 12f

            map.overlays.add(polyline)
        }
        /*roadLines.forEach { road ->
            road.paths.forEach { path ->

                val polyline = Polyline()
                val geoPoints = path.map { (x, y) ->
                    val (lat, lon) = convertToLatLng(x, y)
                    GeoPoint(lat, lon)
                }

                polyline.setPoints(geoPoints)

                val color = try {
                    Color.parseColor(road.colorHex ?: "#FF0000")
                } catch (e: Exception) {
                    Color.RED
                }

                polyline.outlinePaint.color = color
                polyline.outlinePaint.strokeWidth = 20f

                map.overlays.add(polyline)
            }
        }*/

        map.invalidate()
    }
}