package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AppDatabase
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import `is`.hi.hbv601g.icelandicweatherapp.repository.ForecastRepository
import kotlinx.coroutines.launch


class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val forecastDao = AppDatabase.getDatabase(application).getForecastDao()

    private val repository = ForecastRepository(forecastDao)

    private val _forecasts = MutableLiveData<List<ForecastDto>>()
    val forecasts: LiveData<List<ForecastDto>> = _forecasts

    fun loadForecasts(latitude: Double, longitude: Double){
        Log.e("LocationsVM", "loadForecast called")
        viewModelScope.launch {
            try{
                repository.refreshForecast(latitude, longitude)
                val forecasts = repository.loadForecasts()
                Log.d("LocationsVM", "Forecast count: ${forecasts.size}")
                _forecasts.value = forecasts
            } catch(e: Exception) {
                Log.e("LocationsVM", "error loading forecast", e)
                _forecasts.value = emptyList()
            }
        }
    }
}


