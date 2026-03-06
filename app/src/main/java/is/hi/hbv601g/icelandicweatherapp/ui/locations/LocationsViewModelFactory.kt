package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * Factory class used to create instances of LocationsViewModel
 */
class LocationsViewModelFactory (
    //application context passed to the ViewModel
    private val application: Application
): ViewModelProvider.Factory {
    /**
     * Creates the requested ViewModel instance
     * @return a new instance of LocationsViewModel
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //check if the requested VieModel is a LocationsViewModel
        if(modelClass.isAssignableFrom(LocationsViewModel::class.java)){
            // Supprest becouse we ensure the correct type
            @Suppress("UNCHECKED_CAST")
            return LocationsViewModel(application) as T
        }
        // throw an error if an unkown ViewModel type is requested
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}