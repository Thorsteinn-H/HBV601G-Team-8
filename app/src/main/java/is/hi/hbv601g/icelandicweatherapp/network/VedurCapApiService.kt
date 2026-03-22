package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.VedurCapRegionDetailDto
import `is`.hi.hbv601g.icelandicweatherapp.data.VedurCapRegionDto
import retrofit2.http.GET
import retrofit2.http.Path

interface VedurCapApiService {

    @GET("capcreator/forecast_regions/")
    suspend fun getForecastRegions(): List<VedurCapRegionDto>

    @GET("capcreator/forecast_region/{areaId}/")
    suspend fun getForecastRegion(@Path("areaId") areaId: Int): VedurCapRegionDetailDto
}