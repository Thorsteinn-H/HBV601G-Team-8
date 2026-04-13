package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.QuakeDto
import `is`.hi.hbv601g.icelandicweatherapp.data.VolcanoDto
import `is`.hi.hbv601g.icelandicweatherapp.model.Alert
import `is`.hi.hbv601g.icelandicweatherapp.model.RoadCondition
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import `is`.hi.hbv601g.icelandicweatherapp.repository.VedurAlertsRepository
import `is`.hi.hbv601g.icelandicweatherapp.repository.RoadRepository
import kotlinx.coroutines.launch

class MapViewModel: ViewModel()  {

    private val vedurAlertsRepository = VedurAlertsRepository()

    private val _earthquake = MutableLiveData<QuakeDto>()
    val earthquake: LiveData<QuakeDto> = _earthquake

    fun loadEarthquakes(start: String){
        viewModelScope.launch {
            val response = vedurAlertsRepository.getEarthquakes(start)
            if (response != null){
                _earthquake.value = response
            }
        }

    }

    private val _volcano = MutableLiveData<List<VolcanoDto>>()
    val volcano: LiveData<List<VolcanoDto>> = _volcano

    fun loadVolcanos(){
        viewModelScope.launch {
            val response = vedurAlertsRepository.getVolcanos()
            _volcano.value = response
        }

    }

    // repo that handles API calls
    private val roadRepository = RoadRepository()

    //road data
    private val _roads = MutableLiveData<List<RoadCondition>>()
    //immutable liceData
    val roads: LiveData<List<RoadCondition>> = _roads

    fun loadRoads() {
        viewModelScope.launch {
            _roads.value =  roadRepository.getRoadConditions()
        }
    }


    private val _alerts = MutableLiveData<List<Alert>>()
    val alerts: LiveData<List<Alert>> = _alerts

    fun loadAlerts(){
        viewModelScope.launch {
            _alerts.value = vedurAlertsRepository.getAlerts()
        }
    }
}