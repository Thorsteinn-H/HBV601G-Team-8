package `is`.hi.hbv601g.icelandicweatherapp.data

import com.google.gson.annotations.SerializedName

data class NorthernLightsCurrentDto(
    val coordinates: List<List<Double>>
)

data class KpIndexEntry(
    @SerializedName("time_tag") val timeTag: String,
    @SerializedName("Kp") val kp: Double,
    val a_running: Int?,
    val station_count: Int?
)

data class KpForecastEntry(
    @SerializedName("time_tag") val timeTag: String,
    val kp: Double,
    val observed: String?,
    @SerializedName("noaa_scale") val noaaScale: String?
)

data class Kp27DayOutlookEntry(
    val date: String,
    val kp: Int
)

data class Kp27DayOutlookResponse(
    val data: List<Kp27DayOutlookEntry>
)
