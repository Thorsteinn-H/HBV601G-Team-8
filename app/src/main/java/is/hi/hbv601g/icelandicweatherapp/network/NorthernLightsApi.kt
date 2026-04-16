package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.*
import okhttp3.ResponseBody
import retrofit2.http.GET

interface NorthernLightsApi {

    @GET("json/ovation_aurora_latest.json")
    suspend fun getCurrentNorthernLights(): NorthernLightsCurrentDto

    @GET("products/solar-wind/plasma-5-minute.json")
    suspend fun getSolarWind(): List<List<String>>

    @GET("products/solar-wind/mag-5-minute.json")
    suspend fun getIMF(): List<List<String>>

    @GET("products/noaa-planetary-k-index.json")
    suspend fun getKpIndex(): List<KpIndexEntry>

    @GET("products/noaa-planetary-k-index-forecast.json")
    suspend fun getKpForecast(): List<KpForecastEntry>

    @GET("text/27-day-outlook.txt")
    suspend fun getKp27DayOutlookText(): ResponseBody
}