package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.RoadResponse
import retrofit2.http.GET

interface RoadApi {

    @GET("faerd2017_1")
    suspend fun getRoadConditions(): RoadResponse
}