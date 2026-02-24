package `is`.hi.hbv601g.icelandicweatherapp.ui.transform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.repository.AlertRepository
import kotlinx.coroutines.launch

/**
 * Survives configuration changes
 * exposes immutable LiveData to the UI
 * the Fragment observes this ViewModel and never touches the repo directly
 */
class TransformViewModel : ViewModel() {

    private val repository = AlertRepository()

    private val _alerts = MutableLiveData<List<AlertDto>>()

    /**
     * exposed to the UI
     * The fragment can observe but not modify
     */
    val alerts: LiveData<List<AlertDto>> = _alerts

    fun loadAlerts() {
        viewModelScope.launch {
            try {
                // fetch alerts from repository
                _alerts.value = repository.getAlerts()
            } catch (e: Exception) {
                // in case of error, expose an empty list
                _alerts.value = emptyList()
            }
        }
    }
}