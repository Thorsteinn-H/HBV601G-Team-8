package `is`.hi.hbv601g.icelandicweatherapp.model

import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneOffset

/**
 *  utility object for time related functions
 */
object TimeUtils {
    //Returns the current time in UTC truncated to the current hour
    fun currentUtcHour(): String{

        // Create a formatter macthing the met.no time format
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00:00'Z'")
            .withZone(ZoneOffset.UTC)

        // format the current instant into the UTC string
        return formatter.format(Instant.now())
    }
}