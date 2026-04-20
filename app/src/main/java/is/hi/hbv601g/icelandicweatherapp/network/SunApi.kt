package `is`.hi.hbv601g.icelandicweatherapp.network
import retrofit2.http.Query
import `is`.hi.hbv601g.icelandicweatherapp.data.SunDto
import retrofit2.Response
import retrofit2.http.GET


interface SunApi {
    @GET("/json")
    suspend fun getSunsetSunriseToday(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,

    ): Response<SunDto>

}