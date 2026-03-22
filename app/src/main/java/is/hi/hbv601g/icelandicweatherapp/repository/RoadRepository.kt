package `is`.hi.hbv601g.icelandicweatherapp.repository

import android.util.Log
import `is`.hi.hbv601g.icelandicweatherapp.data.RoadResponseDto
import `is`.hi.hbv601g.icelandicweatherapp.model.RoadCondition
import `is`.hi.hbv601g.icelandicweatherapp.model.toRoadCondition
import `is`.hi.hbv601g.icelandicweatherapp.network.RoadApiClient

// Repository responsible for fetching and transformin road data
class RoadRepository {

    //fetches road conditions from the API and maps them
    suspend fun getRoadConditions(): List<RoadCondition>{
        return try {
            //retrofit network reques
            val response = RoadApiClient.api.getRoadConditions()

            //convert each DTO into a clean domain model
            response.features.map{ feature ->
                // MAP DTO
                feature.toRoadCondition()
            }
        } catch (e: Exception){
            Log.e("API_ERROR", "Error loading roads", e)
            throw e
        }
    }
}