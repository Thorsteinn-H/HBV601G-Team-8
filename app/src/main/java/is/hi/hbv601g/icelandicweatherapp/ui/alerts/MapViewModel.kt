package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.QuakeDto
import `is`.hi.hbv601g.icelandicweatherapp.data.VolcanoDto
import `is`.hi.hbv601g.icelandicweatherapp.model.RoadCondition
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import `is`.hi.hbv601g.icelandicweatherapp.repository.RoadRepository
import kotlinx.coroutines.launch

class MapViewModel: ViewModel()  {

    private val vedurApi= VedurApiClient.api

    private val _earthquake = MutableLiveData<QuakeDto>()
    val earthquake: LiveData<QuakeDto> = _earthquake

    fun loadEarthquakes(start: String){
        viewModelScope.launch {
            try {
                val response = vedurApi.getEarthquakes(start, "json")
                if (response.isSuccessful) {
                    _earthquake.value = response.body()
                }
            } catch (e: Exception) {
                // Log or handle error
            }
        }
    }

    private val _volcano = MutableLiveData<List<VolcanoDto>>()
    val volcano: LiveData<List<VolcanoDto>> = _volcano

    fun loadVolcanos(){
        viewModelScope.launch {
            try {
                val response = vedurApi.getVolcanos()
                if (response.isSuccessful) {
                    _volcano.value = response.body()
                }
            } catch (e: Exception) {
                // Handle error
            }
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
            try {
                val result = roadRepository.getRoadConditions()
                _roads.value = result
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}