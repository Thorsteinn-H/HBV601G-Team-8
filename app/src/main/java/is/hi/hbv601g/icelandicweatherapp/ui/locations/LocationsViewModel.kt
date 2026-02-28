package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.util.Log
import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ApiClient
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LocationsViewModel : ViewModel() {
    //Logic hér sem notar api til að sækja
    private val vedurApi=ApiClient.api;

    private val _forecast = MutableLiveData<List<ForecastDto>>()
    val forecast: LiveData<List<ForecastDto>> = _forecast

    fun loadForecasts(region:Int,dayFrom:String,dayTo:String){
        viewModelScope.launch {
            val response = vedurApi.getForecastToday(region, dayFrom, dayTo)
            Log.d("LocationsViewModel", "API response: $response")
            if (response.body() != null){
                _forecast.value = response.body()!!
            }
        }
    }
}


