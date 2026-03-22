package `is`.hi.hbv601g.icelandicweatherapp.model

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto

fun AlertDto.toSeverityLevel(): SeverityLevel {
    return when (severity?.lowercase()) {
        "minor" -> SeverityLevel.MINOR
        "moderate" -> SeverityLevel.MODERATE
        "severe" -> SeverityLevel.SEVERE
        "extreme" -> SeverityLevel.EXTREME
        else -> SeverityLevel.UNKNOWN
    }
}

fun SeverityLevel.toDisplayText(): String {
    return when (this) {
        SeverityLevel.MINOR -> "Green"
        SeverityLevel.MODERATE -> "Yellow"
        SeverityLevel.SEVERE -> "Orange"
        SeverityLevel.EXTREME -> "Red"
        SeverityLevel.UNKNOWN -> "Unknown"
    }
}