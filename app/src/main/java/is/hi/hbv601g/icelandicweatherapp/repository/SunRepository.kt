package `is`.hi.hbv601g.icelandicweatherapp.repository

import android.util.Log
import `is`.hi.hbv601g.icelandicweatherapp.data.SunDto
import `is`.hi.hbv601g.icelandicweatherapp.network.SunApiClient

class SunRepository {

    suspend fun getSun(lat: Double, lng: Double): SunDto? {
        return try{
            val response = SunApiClient.sunApi.getSunsetSunriseToday(lat, lng)

            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        } catch (e: Exception){
            Log.e("SUN_ERROR", "Failed to fetch sun data", e)
            null
        }
    }
}