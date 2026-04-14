package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AppDatabase
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.model.SunInfo
import `is`.hi.hbv601g.icelandicweatherapp.repository.ForecastRepository
import `is`.hi.hbv601g.icelandicweatherapp.repository.SunRepository
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
    private val forecastRepository = ForecastRepository(forecastDao)
    private val sunRepository = SunRepository()

    // Internal mutable LiveData storing the forecast list
    private val _forecasts = MutableLiveData<List<ForecastDto>>()

    // public immutable LiveData observed by the UI
    val forecasts: LiveData<List<ForecastDto>> = _forecasts

    private val _sun = MutableLiveData<SunInfo?>()
    val sun: LiveData<SunInfo?> = _sun

    //loads the full forecast for the given lat- and longitude
    fun loadForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            forecastRepository.refreshForecast(latitude, longitude)
            _forecasts.value = forecastRepository.loadForecasts()

            val sunInfo = try {
                val sunDto = sunRepository.getSun(latitude, longitude)

                SunInfo(
                    sunrise = sunDto?.results?.sunrise,
                    sunset = sunDto?.results?.sunset
                )
            } catch (e: Exception) {
                null
            }

            _sun.value = sunInfo
        }
    }

}