package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemForecastBinding

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
        fun bind(forecast: ForecastDto) {
            binding.textTime.text = forecast.time

            binding.textTemperature.text =
                forecast.temperature?.let { "Temperature $it °C" } ?: "Temperature: N/A"
            binding.textWind.text =
                forecast.windSpeed?.let { "Wind: $it m/s" } ?: "Wind: N/A"

            binding.textPrecipitation.text =
                forecast.precipitation?.let { "Precipitation: $it mm" } ?: "Precipitation: N/A"
        }
    }

    companion object {
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