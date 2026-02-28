package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ApiClient
import kotlinx.coroutines.launch


class LocationsViewModel : ViewModel() {
    //Logic hér sem notar api til að sækja
    private val vedurApi=ApiClient.api

    private val _forecast = MutableLiveData<List<ForecastDto>>()
    val forecast: LiveData<List<ForecastDto>> = _forecast

    fun loadForecasts(region:Int){
        viewModelScope.launch {
            val response = vedurApi.getForecastToday(region)
            if (response.body() != null){
                _forecast.value = response.body()!!
            }
        }
    }
}


