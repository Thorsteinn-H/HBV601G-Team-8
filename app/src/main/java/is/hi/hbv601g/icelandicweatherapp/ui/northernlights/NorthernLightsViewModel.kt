package `is`.hi.hbv601g.icelandicweatherapp.ui.northernlights

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.NorthernLightsCurrentDto
import `is`.hi.hbv601g.icelandicweatherapp.repository.NorthernLightsRepository
import kotlinx.coroutines.launch

class NorthernLightsViewModel : ViewModel() {

    private val repository = NorthernLightsRepository()

    private val _currentNorthernLights = MutableLiveData<NorthernLightsCurrentDto>()
    val currentNorthernLights: LiveData<NorthernLightsCurrentDto> = _currentNorthernLights

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadCurrentNorthernLights() {
        viewModelScope.launch {
            try {
                _currentNorthernLights.value = repository.getCurrentNorthernLights()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load Northern Lights data."
                e.printStackTrace()
            }
        }
    }
}