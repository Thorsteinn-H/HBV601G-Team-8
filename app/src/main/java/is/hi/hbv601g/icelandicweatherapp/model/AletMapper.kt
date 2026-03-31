package `is`.hi.hbv601g.icelandicweatherapp.model

import android.util.Log
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.utilities.parsePolygon
import org.osmdroid.util.GeoPoint

/*fun AlertDto.toSeverityLevel(): SeverityLevel {
    return when (severity?.lowercase()) {
        "minor" -> SeverityLevel.MINOR
        "moderate" -> SeverityLevel.MODERATE
        "severe" -> SeverityLevel.SEVERE
        "extreme" -> SeverityLevel.EXTREME
        else -> SeverityLevel.UNKNOWN
    }
}*/

fun SeverityLevel.toDisplayText(): String {
    return when (this) {
        SeverityLevel.MINOR -> "Green"
        SeverityLevel.MODERATE -> "Yellow"
        SeverityLevel.SEVERE -> "Orange"
        SeverityLevel.EXTREME -> "Red"
        SeverityLevel.UNKNOWN -> "Unknown"
    }
}

fun AlertDto.toAlert(): Alert{
    val polygons = polygon?.mapNotNull { polyString ->
        val parsed = parsePolygon(polyString)

        if (parsed.size > 2) parsed else null
    } ?: emptyList()

    Log.e("ALERT_DEBUG", "Polygons parsed: ${polygons.size}")

    return Alert(
        polygons = polygons,
        severity = severity ?: "Unknown"
    )
}