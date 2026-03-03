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
import `is`.hi.hbv601g.icelandicweatherapp.model.CurrentLocationWeather
import `is`.hi.hbv601g.icelandicweatherapp.model.IcelandLocations
import `is`.hi.hbv601g.icelandicweatherapp.model.TimeUtils
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import `is`.hi.hbv601g.icelandicweatherapp.repository.ForecastRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for loading the current-hour weather for all
 * predefined Icelandic Locations
 */
class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val forecastDao = AppDatabase.getDatabase(application).getForecastDao()

    //Repository handles API calls and database operations
    private val repository = ForecastRepository(forecastDao)

    private val _currentWeather =
        MutableLiveData<List<CurrentLocationWeather>>()

    val currentWeather: LiveData<List<CurrentLocationWeather>> = _currentWeather

    /**
     * Loads the current-hout weather for major Icelandic locations
     * get the current hour
     * fetch forecast for each location on that hour
     * expose the result to the UI
     */
    fun loadCurrentWeatherForAllLocations(){

        viewModelScope.launch {

            val hourPrefix = TimeUtils.currentUtcHour()
            val results = IcelandLocations.majorIcelandLocation.map { location ->

                async {
                    try{
                        repository.refreshForecast(
                            location.latitude,
                            location.longitude
                        )

                        val forecasts = repository.loadForecasts()

                        val current = forecasts.firstOrNull{
                            it.time.startsWith(hourPrefix)
                        }
                        //convert to UI model
                        CurrentLocationWeather(
                            locationName = location.name,
                            temperature = current?.temperature,
                            windSpeed = current?.windSpeed,
                            precipitation = current?.precipitation
                        )
                    } catch (e: Exception) {
                        // If the API/database fail, return empty val
                        CurrentLocationWeather(
                            locationName = location.name,
                            temperature = null,
                            windSpeed = null,
                            precipitation = null
                        )
                    }
                }
            }.awaitAll()
            //publish final list to Fragment UI
            _currentWeather.value = results
        }
    }
}


