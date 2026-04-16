package `is`.hi.hbv601g.icelandicweatherapp.utilities

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter



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

    fun fancyStringFormat(date: String): String{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")
            .withZone(ZoneOffset.UTC)
        return formatter.format(Instant.parse(date))
    }

    fun fancyStringFormatDate(date: String): String{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return formatter.format(LocalDate.parse(date))

    }
}