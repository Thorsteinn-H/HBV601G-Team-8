package `is`.hi.hbv601g.icelandicweatherapp.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemWeatherAlertBinding

/**
 * RecyclerView adapter for displaying favourite alerts.
 *
 * Uses ListAdapter + DiffUtil for efficient updates.
 */
class FavouritesAdapter :
    ListAdapter<AlertDto, FavouritesAdapter.AlertViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding = ItemWeatherAlertBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder that binds AlertDto data to the UI.
     */
    class AlertViewHolder(
        private val binding: ItemWeatherAlertBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(alert: AlertDto) {
            binding.textHeadline?.text =
                alert.headline ?: "No headline"

            binding.textSeverity?.text =
                "Severity: ${alert.severity ?: "Unknown"}"

            binding.textUrgency?.text =
                "Urgency: ${alert.urgency ?: "Unknown"}"
        }
    }

    companion object {
        /**
         * DiffUtil for calculating list changes efficiently.
         */
        private val DiffCallback = object : DiffUtil.ItemCallback<AlertDto>() {

            override fun areItemsTheSame(
                oldItem: AlertDto,
                newItem: AlertDto
            ): Boolean {
                // If you have a database ID, use it here
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AlertDto,
                newItem: AlertDto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}