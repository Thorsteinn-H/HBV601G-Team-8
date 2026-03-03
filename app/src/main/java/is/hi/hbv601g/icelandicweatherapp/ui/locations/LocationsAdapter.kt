package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemLocationsBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import `is`.hi.hbv601g.icelandicweatherapp.model.CurrentLocationWeather

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

        fun bind(item: CurrentLocationWeather){
            binding.textLocationName.text = item.locationName

            binding.textTemperature.text =
                "Temperature: ${item.temperature ?: "N/A"} °C"
            binding.textWind.text =
                "Wind: ${item.windSpeed ?: "N/A"}"
            binding.textPrecipitation.text =
                "Precipitation: ${item.precipitation ?: "N/A"} mm"

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
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