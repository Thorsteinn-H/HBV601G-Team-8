package `is`.hi.hbv601g.icelandicweatherapp.data

import com.google.gson.annotations.SerializedName

data class AlertDto(
    @SerializedName("headline_en")
    val headlin: String?,
    @SerializedName("description_en")
    val description: String?,
    val severity: String?,
    val urgency: String?
)
