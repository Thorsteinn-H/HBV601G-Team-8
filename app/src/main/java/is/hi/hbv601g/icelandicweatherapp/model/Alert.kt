package `is`.hi.hbv601g.icelandicweatherapp.model

import org.osmdroid.util.GeoPoint

data class Alert(
    // a list of geopoint which form a polygon
    val polygons: List<List<GeoPoint>>,
    // severity of alert
    val severity: String,
    // description in english
    val descriptionEn: String
)
