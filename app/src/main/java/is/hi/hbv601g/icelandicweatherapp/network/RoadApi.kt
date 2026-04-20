package `is`.hi.hbv601g.icelandicweatherapp.network

import retrofit2.http.Query
import `is`.hi.hbv601g.icelandicweatherapp.data.RoadResponseDto
import retrofit2.http.GET

interface RoadApi {


    @GET("arcgis/rest/services/data/faerd/FeatureServer/15/query")
    suspend fun getRoadConditions(
        @Query("where") where: String,
        @Query("outFields") outFields: String,
        @Query("returnGeometry") returnGeometry: Boolean,
        @Query("f") format: String
    ): RoadResponseDto
}