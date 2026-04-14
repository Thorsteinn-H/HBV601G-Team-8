package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemForecastBinding
import `is`.hi.hbv601g.icelandicweatherapp.model.WeatherUtils
/**
 * RecyclerView Adaptar responsible for displaying the full forecast for a selected location
 *
 *
 */
class ForecastAdapter :
    ListAdapter<ForecastDto, ForecastAdapter.ForecastViewHolder>(DiffCallback) {

    /**
     * creates a new ViewHolder when RecyclerView needs one
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder representing one forecast row
     */
    class ForecastViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        // populates the UI elements with forecast data

        fun bind(forecast: ForecastDto) {

            //display the forecast timestamp
            binding.textTime.text = forecast.time
            val context = binding.root.context
            val hitastigStilling = WeatherUtils.readHitastigSettings(context);

            //display temperature in celsius
            binding.textTemperature.text =
                forecast.temperature?.let {
                    (
                            if(hitastigStilling==1){"Temperature $it °C"}
                            else {
                                val fahrenheit= WeatherUtils.calculateFahrenheit(it)
                                "Temperature $fahrenheit °F"

                    })
                } ?: "Temperature: N/A"

            // display feels like temp
            val feelsLike = WeatherUtils.calculateFeelsLike(
                forecast.temperature,
                forecast.windSpeed,
                forecast.relativeHumidity
            )
            val feelsLikeText = feelsLike?.let {
                (
                        if(hitastigStilling==1){"Feels like:  $it °C"}
                        else {
                            val fahrenheit= WeatherUtils.calculateFahrenheit(it)
                            "Feels like:  $fahrenheit °F"

                        })
            } ?: "Feels like: N/A"
            binding.textFeelsLike.text = feelsLikeText

            //display wind speed in meters per second
            binding.textWind.text =
                forecast.windSpeed?.let { "Wind: $it m/s" } ?: "Wind: N/A"

            // Display percipitation in millimeters
            binding.textPrecipitation.text =
                forecast.precipitation?.let { "Precipitation: $it mm" } ?: "Precipitation: N/A"
        }
    }

    companion object {
        /**
         * diffutil callback used by ListAdapter to determin
         * what items have chaneged
         */
        private val DiffCallback = object : DiffUtil.ItemCallback<ForecastDto>(){
            override fun areItemsTheSame(oldItem: ForecastDto, newItem: ForecastDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ForecastDto, newItem: ForecastDto): Boolean {
                return oldItem == newItem
            }
        }

    }
}
