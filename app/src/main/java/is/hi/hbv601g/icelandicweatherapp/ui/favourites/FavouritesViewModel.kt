package `is`.hi.hbv601g.icelandicweatherapp.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.repository.AlertRepository
import `is`.hi.hbv601g.icelandicweatherapp.data.AppDatabase
import kotlinx.coroutines.launch
import androidx.lifecycle.AndroidViewModel
import android.app.Application

/**
 * Survives configuration changes
 * exposes immutable LiveData to the UI
 * the Fragment observes this ViewModel and never touches the repo directly
 */
class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private val alertDao = AppDatabase.getDatabase(application).getAlertDao()
    private val repository = AlertRepository(alertDao)

    private val _alerts = MutableLiveData<List<AlertDto>>()

    /**
     * exposed to the UI
     * The fragment can observe but not modify
     */
    val alerts: LiveData<List<AlertDto>> = _alerts

    fun loadAlerts() {
        viewModelScope.launch {
            try {
                repository.saveAlerts()
                // fetch alerts from repository
                _alerts.value = repository.loadAlerts()
            } catch (e: Exception) {
                // in case of error, expose an empty list
                _alerts.value = emptyList()
            }
        }
    }
}