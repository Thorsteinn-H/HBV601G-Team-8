package `is`.hi.hbv601g.icelandicweatherapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/**
 * Represents a SINGLE weather alert returned by Icelandic Met Office
 * DTO = Data transfer Object
 *
 * data mirrors the JSON structure
 * No business logic
 *
 * alot more that can go in here the data in theJSON
 * is large
 */
data class AlertDto(
    //@PrimaryKey(autoGenerate = true)
    //val id: Int = 0,
    //English headline for the alert
    //@SerializedName("headline_en")
    //val headline: String?,
    // English description for the alert
    //@SerializedName("description_en")
    //val description: String?,
    //val urgency: String?,

    // an area that an alert is going on inn
    @SerializedName("polygon")
    val polygon: List<String>?,

    // severity of said alert
    @SerializedName("severity")
    val severity: String?,

    //description in english of said alert
    @SerializedName("description_en")
    val descriptionEn: String?
)

