package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.NorthernLightsCurrentDto
import retrofit2.http.GET

interface NorthernLightsApi {

    @GET("json/ovation_aurora_latest.json")
    suspend fun getCurrentNorthernLights(): NorthernLightsCurrentDto
}