package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.NorthernLightsCurrentDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ApiClient

class NorthernLightsRepository {

    suspend fun getCurrentNorthernLights(): NorthernLightsCurrentDto {
        return ApiClient.northernLightsApi.getCurrentNorthernLights()
    }
}