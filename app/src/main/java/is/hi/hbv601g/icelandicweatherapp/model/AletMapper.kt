package `is`.hi.hbv601g.icelandicweatherapp.model

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.utilities.parsePolygon

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
    val polygons = info?.flatMap { info ->
        info.area?.mapNotNull { area ->
            area.polygon?.let { parsePolygon(it) }
        } ?: emptyList()
    } ?: emptyList()
    val severity = info?.firstOrNull()?.severity ?: "Unkown"

    return Alert(
        polygons = polygons,
        severity = severity
    )
}