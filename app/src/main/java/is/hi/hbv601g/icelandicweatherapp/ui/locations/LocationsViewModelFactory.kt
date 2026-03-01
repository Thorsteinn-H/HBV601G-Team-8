package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationsViewModelFactory (
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LocationsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LocationsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}