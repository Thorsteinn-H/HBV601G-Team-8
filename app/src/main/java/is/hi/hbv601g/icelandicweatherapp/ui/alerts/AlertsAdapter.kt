package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemAlertBinding
import `is`.hi.hbv601g.icelandicweatherapp.ui.favourites.FavouritesAdapter

/**
 * RecyclerView adapter responsivle for displaying a list of weather alerts
 */
class AlertsAdapter :
    ListAdapter<AlertDto, AlertsAdapter.AlertViewHolder>(DiffCallback){

    /**
     * new ViewHodler when RecyclerView needs one
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlertViewHolder {
        val binding = ItemAlertBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlertViewHolder(binding)
    }

    /**
     * binds alert data to the ViewHolder
     */
    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHodler representing one alert item
     */
    class AlertViewHolder(
        private val binding: ItemAlertBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alert: AlertDto) {
            binding.textHeadLine.text = alert.headline
            binding.textSeverity.text = "Severity: ${alert.severity}"
            binding.textDescription.text = alert.description
        }
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<AlertDto>(){
            override fun areItemsTheSame(oldItem: AlertDto, newItem: AlertDto): Boolean {
                return oldItem.headline == newItem.headline
            }

            override fun areContentsTheSame(oldItem: AlertDto, newItem: AlertDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}