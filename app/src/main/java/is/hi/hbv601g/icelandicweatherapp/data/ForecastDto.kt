package `is`.hi.hbv601g.icelandicweatherapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Single forecast entry
 */
@Entity(tableName = "forecasts")
data class ForecastDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //ISO-8601( e.g. 2026-02-26T12:00:00Z)
    val time: String,
    //default in Celsius
    val temperature: Double?,
    // meters per second
    val windSpeed: Double?,
    // amount in millimeters
    val precipitation: Double?,
    //weather symbol code
    val symbolCode: String?,
    // percentage
    val relativeHumidity: Double? = null
)