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

class LocationsAdapter :
    ListAdapter<ForecastDto, LocationsAdapter.ForecastViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder{
        val binding = ItemLocationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ForecastViewHolder(
        private val binding: ItemLocationsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: ForecastDto){
            binding.textTime.text = forecast.time

            binding.textTemperature.text =
                "Temperature: ${forecast.temperature ?: "-"} °C"

            binding.textPrecipitation.text =
                "Precipitation: ${forecast.precipitation ?: "-"} mm"
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