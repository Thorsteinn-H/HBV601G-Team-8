package `is`.hi.hbv601g.icelandicweatherapp.utilities

import org.osmdroid.util.GeoPoint

fun parsePolygon(polygon: String): List<GeoPoint> {
    return polygon.split(" ").mapNotNull { coord ->
        val parts = coord.split(",")
        if(parts.size == 2){
            val lat = parts[0].toDoubleOrNull()
            val lon = parts[1].toDoubleOrNull()
            if(lat != null && lon != null){
                GeoPoint(lat, lon)
            } else null
        } else null
    }
}