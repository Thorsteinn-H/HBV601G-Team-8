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


class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val forecastDao = AppDatabase.getDatabase(application).getForecastDao()

    private val repository = ForecastRepository(forecastDao)

    private val _currentWeather =
        MutableLiveData<List<CurrentLocationWeather>>()

    val currentWeather: LiveData<List<CurrentLocationWeather>> = _currentWeather

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

                        CurrentLocationWeather(
                            locationName = location.name,
                            temperature = current?.temperature,
                            windSpeed = current?.windSpeed,
                            precipitation = current?.precipitation
                        )
                    } catch (e: Exception) {
                        CurrentLocationWeather(
                            locationName = location.name,
                            temperature = null,
                            windSpeed = null,
                            precipitation = null
                        )
                    }
                }
            }.awaitAll()
            _currentWeather.value = results
        }
    }
}


