package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import `is`.hi.hbv601g.icelandicweatherapp.data.RoadResponseDto
import `is`.hi.hbv601g.icelandicweatherapp.repository.RoadRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import `is`.hi.hbv601g.icelandicweatherapp.model.RoadCondition
import `is`.hi.hbv601g.icelandicweatherapp.network.RoadApiClient


//ViewModel responsible for managing road data for the UI
class RoadViewModel: ViewModel() {

    // repo that handles API calls
    private val repository = RoadRepository()

    //road data
    private val _roads = MutableLiveData<List<RoadCondition>>()
    //immutable liceData
    val roads: LiveData<List<RoadCondition>> = _roads

    fun loadRoads() {
        viewModelScope.launch {
            val result = repository.getRoadConditions()
            _roads.value = result
        }
    }
}