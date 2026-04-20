package `is`.hi.hbv601g.icelandicweatherapp.model

import android.util.Log
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.utilities.parsePolygon
import org.osmdroid.util.GeoPoint

// was used before, redundant now
fun SeverityLevel.toDisplayText(): String {
    return when (this) {
        SeverityLevel.MINOR -> "Green"
        SeverityLevel.MODERATE -> "Yellow"
        SeverityLevel.SEVERE -> "Orange"
        SeverityLevel.EXTREME -> "Red"
        SeverityLevel.UNKNOWN -> "Unknown"
    }
}

// function to convert API DTO into domain model used by the app
fun AlertDto.toAlert(): Alert{
    // convert polygon from API into list of GeoPoints
    val polygons = polygon?.mapNotNull { polyString ->
        // Convert string of lats and longs into a list of geopoints
        val parsed = parsePolygon(polyString)

        // polygons need 3 or more points
        if (parsed.size > 2) parsed else null
    } ?: emptyList() // if polygon is null return empty list


    return Alert(
        // list of drawable polygons for the map
        polygons = polygons,
        // if API value is missing
        severity = severity ?: "Unknown",
        // if API value is missing
        descriptionEn = descriptionEn ?: "No description"
    )
}