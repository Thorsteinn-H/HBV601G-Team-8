package `is`.hi.hbv601g.icelandicweatherapp.ui.transform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.repository.AlertRepository
import kotlinx.coroutines.launch

class TransformViewModel : ViewModel() {

    private val repository = AlertRepository()

    private val _alerts = MutableLiveData<List<AlertDto>>()
    val alerts: LiveData<List<AlertDto>> = _alerts

    fun loadAlerts() {
        viewModelScope.launch {
            try {
                _alerts.value = repository.getAlerts()
            } catch (e: Exception) {
                _alerts.value = emptyList()
            }
        }
    }
}