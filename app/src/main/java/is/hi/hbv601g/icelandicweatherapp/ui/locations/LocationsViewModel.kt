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

    // access ForecastDao from Room db
    private val forecastDao = AppDatabase.getDatabase(application).getForecastDao()

    //Repository handles API calls and database operations
    private val repository = ForecastRepository(forecastDao)

    //mutable LiceData holding the current weather for all locations
    private val _currentWeather =
        MutableLiveData<List<CurrentLocationWeather>>()

    // public immutable LiveData observed by the UI
    val currentWeather: LiveData<List<CurrentLocationWeather>> = _currentWeather

    /**
     * Loads the current-hout weather for major Icelandic locations
     * get the current hour
     * fetch forecast for each location on that hour
     * expose the result to the UI
     */
    fun loadAllWeatherWithUserLocation(
        latitude : Double,
        longitude : Double
    ){
        viewModelScope.launch {

            // get the curent hour formatted like the met.no
            val hourPrefix = TimeUtils.currentUtcHour()

            val userLocationWeather = try {
                repository.refreshForecast(latitude, longitude)
                val forecasts = repository.loadForecasts()

                val current = forecasts.firstOrNull {
                    it.time.startsWith(hourPrefix)
                }

                CurrentLocationWeather(
                    locationName = "📍 My Location",
                    temperature = current?.temperature,
                    windSpeed = current?.windSpeed,
                    precipitation = current?.precipitation
                )
            } catch (e: Exception) {
                null
            }
            // fetch forecasts for all Icelandic Locations
            val locations = IcelandLocations.majorIcelandLocation.map { location ->

                async {
                    try{
                        //refresh forecast data from the API
                        repository.refreshForecast(
                            location.latitude,
                            location.longitude
                        )

                        //Load forecast from local database
                        val forecasts = repository.loadForecasts()

                        // find forecast that matches the current hour
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
            val results = if(userLocationWeather != null) {
                listOf(userLocationWeather) + locations
            } else {
                locations
            }
            _currentWeather.value = results
        }
    }

}


