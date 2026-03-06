package `is`.hi.hbv601g.icelandicweatherapp.ui.sun

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.SunDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentSunBinding
import `is`.hi.hbv601g.icelandicweatherapp.network.SunApiClient
import kotlinx.coroutines.launch

class SunViewModel: ViewModel() {
    private val sunApi= SunApiClient.sunApi

    private val _sun = MutableLiveData<SunDto>()
    val sun: LiveData<SunDto> = _sun

    fun loadSun(lat: Double,lng: Double){
        viewModelScope.launch {
            val response = sunApi.getSunsetSunriseToday(lat,lng)
            Log.d("Api", "Code: ${response.code()}")
            Log.d("Api", "Body: ${response.body()}")

            if (response.body() != null){
                _sun.value = response.body()!!
            }
        }

    }

}