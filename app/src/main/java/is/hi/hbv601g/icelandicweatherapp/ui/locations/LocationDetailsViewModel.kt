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

    // access ForecastDAO from the Room database
    private val forecastDao = AppDatabase.getDatabase(application).getForecastDao()

    //Repository handles communication with the API and Database
    private val repository = ForecastRepository(forecastDao)

    // Internal mutable LiveData storing the forecast list
    private val _forecasts = MutableLiveData<List<ForecastDto>>()

    // public immutable LiveData observed by the UI
    val forecasts: LiveData<List<ForecastDto>> = _forecasts

    //loads the full forecast for the given lat- and longitude
    fun loadForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repository.refreshForecast(latitude, longitude)
            _forecasts.value = repository.loadForecasts()
        }
    }

}