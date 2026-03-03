package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AppDatabase
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.repository.ForecastRepository
import kotlinx.coroutines.launch

/**
 * Resposible for loading and exposing the full forecast
 * for a single location
 */
class LocationDetailsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val forecastDao = AppDatabase.getDatabase(application).getForecastDao()

    private val repository = ForecastRepository(forecastDao)

    private val _forecasts = MutableLiveData<List<ForecastDto>>()

    val forecasts: LiveData<List<ForecastDto>> = _forecasts

    //loads the full forecast for the given lat- and longitude
    fun loadForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repository.refreshForecast(latitude, longitude)
            _forecasts.value = repository.loadForecasts()
        }
    }

}