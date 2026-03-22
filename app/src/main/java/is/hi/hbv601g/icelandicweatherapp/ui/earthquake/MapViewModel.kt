package `is`.hi.hbv601g.icelandicweatherapp.ui.earthquake
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.hi.hbv601g.icelandicweatherapp.data.QuakeDto
import `is`.hi.hbv601g.icelandicweatherapp.data.VolcanoDto
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import kotlinx.coroutines.launch

class MapViewModel: ViewModel()  {

    private val vedurApi= VedurApiClient.api

    private val _earthquake = MutableLiveData<QuakeDto>()
    val earthquake: LiveData<QuakeDto> = _earthquake

    fun loadEarthquakes(start: String){
        viewModelScope.launch {
            val response = vedurApi.getEarthquakes(start)

            _earthquake.value = response.body()

        }

    }

    private val _volcano = MutableLiveData<List<VolcanoDto>>()
    val volcano: LiveData<List<VolcanoDto>> = _volcano

    fun loadVolcanos(){
        viewModelScope.launch {
            val response = vedurApi.getVolcanos()

            _volcano.value = response.body()

        }

    }
}