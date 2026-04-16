package `is`.hi.hbv601g.icelandicweatherapp.ui.northernlights

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.*
import `is`.hi.hbv601g.icelandicweatherapp.repository.NorthernLightsRepository
import kotlinx.coroutines.launch

class NorthernLightsViewModel : ViewModel() {

    private val repository = NorthernLightsRepository()

    private val _currentNorthernLights = MutableLiveData<NorthernLightsCurrentDto>()
    val currentNorthernLights: LiveData<NorthernLightsCurrentDto> = _currentNorthernLights

    private val _solarWind = MutableLiveData<List<List<String>>>()
    val solarWind: LiveData<List<List<String>>> = _solarWind

    private val _imf = MutableLiveData<List<List<String>>>()
    val imf: LiveData<List<List<String>>> = _imf

    private val _kpIndexEntries = MutableLiveData<List<KpIndexEntry>>()
    val kpIndexEntries: LiveData<List<KpIndexEntry>> = _kpIndexEntries

    private val _kpForecastEntries = MutableLiveData<List<KpForecastEntry>>()
    val kpForecastEntries: LiveData<List<KpForecastEntry>> = _kpForecastEntries

    private val _kp27DayEntries = MutableLiveData<List<Kp27DayOutlookEntry>>()
    val kp27DayEntries: LiveData<List<Kp27DayOutlookEntry>> = _kp27DayEntries

    private val _cloudForecast = MutableLiveData<CloudResponse>()
    val cloudForecast: LiveData<CloudResponse> = _cloudForecast

    private val _detailedClouds = MutableLiveData<OpenMeteoResponse>()
    val detailedClouds: LiveData<OpenMeteoResponse> = _detailedClouds

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadAllData() {
        viewModelScope.launch {
            launch {
                try {
                    _currentNorthernLights.value = repository.getCurrentNorthernLights()
                } catch (e: Exception) {
                    _errorMessage.postValue("Ekki tókst að hlaða Norðurljós: ${e.message}")
                }
            }
            launch {
                try {
                    _solarWind.value = repository.getSolarWind()
                } catch (e: Exception) { }
            }
            launch {
                try {
                    _imf.value = repository.getIMF()
                } catch (e: Exception) { }
            }
            launch {
                try {
                    _kpIndexEntries.value = repository.getKpIndex()
                } catch (e: Exception) { }
            }
            launch {
                try {
                    _kpForecastEntries.value = repository.getKpForecast()
                } catch (e: Exception) { }
            }
            launch {
                try {
                    _kp27DayEntries.value = repository.getKp27DayOutlook()
                } catch (e: Exception) { }
            }
            launch {
                try {
                    _cloudForecast.value = repository.getCloudForecast(64.1265, -21.8174)
                } catch (e: Exception) {
                    _errorMessage.postValue("Ekki tókst að hlaða ský: ${e.message}")
                }
            }
            launch {
                try {
                    _detailedClouds.value = repository.getDetailedClouds(64.1265, -21.8174)
                } catch (e: Exception) {
                    _errorMessage.postValue("Ekki tókst að hlaða nákvæm ský: ${e.message}")
                }
            }
        }
    }
}