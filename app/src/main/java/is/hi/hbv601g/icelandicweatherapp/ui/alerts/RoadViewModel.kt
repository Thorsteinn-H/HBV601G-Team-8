package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import `is`.hi.hbv601g.icelandicweatherapp.data.RoadConditionDto
import `is`.hi.hbv601g.icelandicweatherapp.repository.RoadRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel

class RoadViewModel: ViewModel() {

    private val repository = RoadRepository()

    private val _roads = MutableLiveData<List<RoadConditionDto>>()
    val roads: LiveData<List<RoadConditionDto>> = _roads

    fun loadRoads() {
        viewModelScope.launch {
            try {
                val data = repository.getRoadConditions()
                _roads.value = data
            } catch (e: Exception){
                Log.e("Road", "Error", e)
            }
        }
    }
}