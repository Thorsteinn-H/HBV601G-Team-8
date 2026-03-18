package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.data.AppDatabase
import `is`.hi.hbv601g.icelandicweatherapp.repository.AlertRepository
import kotlinx.coroutines.launch

class AlertsViewModel(application: Application) : AndroidViewModel(application) {

    private val alertDao = AppDatabase.getDatabase(application).getAlertDao()

    private val repository = AlertRepository(alertDao)

    private val _alerts = MutableLiveData<List<AlertDto>>()
    val alerts: LiveData<List<AlertDto>> = _alerts

    fun loadAlerts() {
        viewModelScope.launch {
            repository.refreshAlerts()
            _alerts.value = repository.getAlerts()
        }
    }
}