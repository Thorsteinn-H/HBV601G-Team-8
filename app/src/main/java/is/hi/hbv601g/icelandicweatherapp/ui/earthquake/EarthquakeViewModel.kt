package `is`.hi.hbv601g.icelandicweatherapp.ui.earthquake

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.QuakeDto
import `is`.hi.hbv601g.icelandicweatherapp.network.SunApiClient
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurAlertsApi
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import kotlinx.coroutines.launch

class EarthquakeViewModel: ViewModel()  {

    private val vedurApi= VedurApiClient.api

    private val _earthquake = MutableLiveData<QuakeDto>()
    val earthquake: LiveData<QuakeDto> = _earthquake

    fun loadEarthquakes(start: String){
        viewModelScope.launch {
            val response = vedurApi.getEarthquakes(start)

            _earthquake.value = response.body()

        }

    }
}