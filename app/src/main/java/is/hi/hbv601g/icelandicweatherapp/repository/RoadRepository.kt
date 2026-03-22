package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.RoadConditionDto
import `is`.hi.hbv601g.icelandicweatherapp.network.RoadApiClient

class RoadRepository {

    suspend fun getRoadConditions(): List<RoadConditionDto>{
        return RoadApiClient.api.getRoadConditions().roads
    }
}