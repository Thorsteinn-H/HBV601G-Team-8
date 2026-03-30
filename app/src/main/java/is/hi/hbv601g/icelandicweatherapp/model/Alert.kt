package `is`.hi.hbv601g.icelandicweatherapp.model

import org.osmdroid.util.GeoPoint

data class Alert(
    val polygons: List<List<GeoPoint>>,
    val severity: String
)
