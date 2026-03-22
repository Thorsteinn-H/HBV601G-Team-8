package `is`.hi.hbv601g.icelandicweatherapp.ui.volcano

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.QuakeDto
import `is`.hi.hbv601g.icelandicweatherapp.data.VolcanoDto
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import kotlinx.coroutines.launch

class VolcanoViewModel: ViewModel() {


    private val vedurApi= VedurApiClient.api

    private val _volcano = MutableLiveData<List<VolcanoDto>>()
    val volcano: LiveData<List<VolcanoDto>> = _volcano

    fun loadVolcanos(){
        viewModelScope.launch {
            val response = vedurApi.getVolcanos()

            Log.d("volcano", response.body().toString())


            _volcano.value = response.body()

        }

    }


}