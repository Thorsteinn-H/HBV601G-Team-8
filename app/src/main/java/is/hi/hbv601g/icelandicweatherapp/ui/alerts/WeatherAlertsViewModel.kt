package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.data.AppDatabase
import `is`.hi.hbv601g.icelandicweatherapp.repository.AlertRepository
import kotlinx.coroutines.launch

/**
 *  ViewModel responsible for managing weather alert data
 *
 *  Extends AndroidViewModel to access Application context
 */
class WeatherAlertsViewModel(application: Application) : AndroidViewModel(application) {

    // access AlertDao from ROOM db
    private val alertDao = AppDatabase.getDatabase(application).getAlertDao()

    // handles API calls and db operations
    private val repository = AlertRepository(alertDao)

    // Internal mutable LiveData holing alert list
    private val _alerts = MutableLiveData<List<AlertDto>>()
    val alerts: LiveData<List<AlertDto>> = _alerts

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * loads weather allerts from the repository
     */
    fun loadAlerts() {
        viewModelScope.launch {
            try {
                //fetch fresh data fromAPI
                repository.refreshAlerts()
                // Load alerts from db and expose to UI
                _alerts.value = repository.getAlerts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load alerts: ${e.message}"
                // Even on error, try to show cached data
                _alerts.value = repository.getAlerts()
            }
        }
    }
}