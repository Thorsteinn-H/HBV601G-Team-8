package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemLocationsBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import `is`.hi.hbv601g.icelandicweatherapp.model.CurrentLocationWeather
import `is`.hi.hbv601g.icelandicweatherapp.model.WeatherUtils

/**
 * RecyclerView adapter for displaying current weather for all
 * icelandic locations
 */
class LocationsAdapter(
    private val onItemClick: (CurrentLocationWeather) -> Unit
) : ListAdapter<CurrentLocationWeather, LocationsAdapter.ForecastViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder{
        val binding = ItemLocationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // pass click callback down to ViewHolder
        return ForecastViewHolder(binding, onItemClick)
    }

    // binds weather data to the ViewHolder
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder representin one row, one location
     */
    class ForecastViewHolder(
        private val binding: ItemLocationsBinding,
        private val onItemClick: (CurrentLocationWeather) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        // populates UI elements with weather data
        val context = binding.root.context
        val hitastigStilling = WeatherUtils.readHitastigSettings(context);
        fun bind(item: CurrentLocationWeather){
            //display location name
            binding.textLocationName.text = item.locationName

            // display temp in celsius
            binding.textTemperature.text =
                "Temperature: ${item.temperature ?: "N/A"} °C"

            binding.textTemperature.text =
                item.temperature?.let {
                    (
                            if(hitastigStilling==1){"Temperature $it °C"}
                            else {
                                val fahrenheit= WeatherUtils.calculateFahrenheit(it)
                                "Temperature $it °F"

                            })
                } ?: "Temperature: N/A"


            // display feels like temp
            val feelsLikeText = item.feelsLike?.let {(
                if(hitastigStilling==1){"Feels like: $it °C"}
                else {
                    val fahrenheit= WeatherUtils.calculateFahrenheit(it)
                    "Feels like: $it °F"

                })
            } ?: "Feels like: N/A"
            binding.textFeelsLike.text = feelsLikeText

            //display wind speed
            binding.textWind.text =
                "Wind: ${item.windSpeed ?: "N/A"} m/s"

            //display precipitation
            binding.textPrecipitation.text =
                "Precipitation: ${item.precipitation ?: "N/A"} mm"

            //handle click event for this location
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        /**
         * callback used by ListAdapter to efficiently determine what items have changed
         */
        private val DiffCallback = object : DiffUtil.ItemCallback<CurrentLocationWeather>(){

            override fun areItemsTheSame(oldItem: CurrentLocationWeather, newItem: CurrentLocationWeather): Boolean {
                return oldItem.locationName == newItem.locationName
            }

            override fun areContentsTheSame(oldItem: CurrentLocationWeather, newItem: CurrentLocationWeather): Boolean {
                return oldItem == newItem
            }
        }
    }
}
