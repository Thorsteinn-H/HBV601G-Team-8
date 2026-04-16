package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.*
import `is`.hi.hbv601g.icelandicweatherapp.network.ApiClient
import okhttp3.ResponseBody

class NorthernLightsRepository {

    suspend fun getCurrentNorthernLights(): NorthernLightsCurrentDto {
        return ApiClient.northernLightsApi.getCurrentNorthernLights()
    }

    suspend fun getSolarWind(): List<List<String>> {
        return ApiClient.northernLightsApi.getSolarWind()
    }

    suspend fun getIMF(): List<List<String>> {
        return ApiClient.northernLightsApi.getIMF()
    }

    suspend fun getKpIndex(): List<KpIndexEntry> {
        return ApiClient.northernLightsApi.getKpIndex()
    }

    suspend fun getKpForecast(): List<KpForecastEntry> {
        return ApiClient.northernLightsApi.getKpForecast()
    }

    suspend fun getKp27DayOutlook(): List<Kp27DayOutlookEntry> {
        return try {
            val response = ApiClient.northernLightsApi.getKp27DayOutlookText()
            val text = response.string()
            parse27DayOutlookText(text)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun parse27DayOutlookText(text: String): List<Kp27DayOutlookEntry> {
        val entries = mutableListOf<Kp27DayOutlookEntry>()
        val lines = text.lines()
        
        // Find the start of the data table (lines that start with a year like 2026)
        val dateRegex = Regex("""^(\d{4})\s+([A-Z][a-z]{2})\s+(\d{1,2})\s+\d+\s+\d+\s+(\d+)""")
        
        for (line in lines) {
            val match = dateRegex.find(line.trim())
            if (match != null) {
                val year = match.groupValues[1]
                val monthStr = match.groupValues[2]
                val day = match.groupValues[3]
                val kp = match.groupValues[4].toInt()

                val month = when (monthStr) {
                    "Jan" -> "01"; "Feb" -> "02"; "Mar" -> "03"; "Apr" -> "04"
                    "May" -> "05"; "Jun" -> "06"; "Jul" -> "07"; "Aug" -> "08"
                    "Sep" -> "09"; "Oct" -> "10"; "Nov" -> "11"; "Dec" -> "12"
                    else -> "01"
                }
                
                val formattedDate = "$year-$month-${day.padStart(2, '0')}"
                entries.add(Kp27DayOutlookEntry(formattedDate, kp))
            }
        }
        return entries
    }

    suspend fun getCloudForecast(lat: Double, lon: Double): CloudResponse {
        val apiKey = "6f49de500392f067bbf01d58eb7234cc"
        return ApiClient.weatherApi.getCloudForecast(lat, lon, apiKey)
    }

    suspend fun getDetailedClouds(lat: Double, lon: Double): OpenMeteoResponse {
        return ApiClient.openMeteoApi.getDetailedClouds(
            lat,
            lon,
            "cloud_cover,cloud_cover_low,cloud_cover_mid,cloud_cover_high",
            1
        )
    }
}