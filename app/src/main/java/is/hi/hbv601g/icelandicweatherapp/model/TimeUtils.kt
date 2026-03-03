package `is`.hi.hbv601g.icelandicweatherapp.model

import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneOffset

object TimeUtils {
    //Returns the current time in UTC truncated to the current hour
    fun currentUtcHour(): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00:00'Z'")
            .withZone(ZoneOffset.UTC)
        return formatter.format(Instant.now())
    }
}