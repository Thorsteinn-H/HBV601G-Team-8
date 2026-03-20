package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemAlertBinding
import `is`.hi.hbv601g.icelandicweatherapp.model.SeverityLevel
import `is`.hi.hbv601g.icelandicweatherapp.model.toDisplayText
import `is`.hi.hbv601g.icelandicweatherapp.model.toSeverityLevel

/**
 * RecyclerView adapter responsivle for displaying a list of weather alerts
 */
class WeatherAlertsAdapter :
    ListAdapter<AlertDto, WeatherAlertsAdapter.AlertViewHolder>(DiffCallback){

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
            binding.textDescription.text = alert.description

            val severityLvl = alert.toSeverityLevel()

            binding.textSeverity.text = "Severity: ${severityLvl.toDisplayText()}"

            val context = binding.root.context
            // ✅ Color mapping
            when (alert.toSeverityLevel()) {
                SeverityLevel.MINOR -> binding.textSeverity.setTextColor(ContextCompat.getColor(context, R.color.severity_green))
                SeverityLevel.MODERATE -> binding.textSeverity.setTextColor(ContextCompat.getColor(context, R.color.severity_yellow))
                SeverityLevel.SEVERE -> binding.textSeverity.setTextColor(ContextCompat.getColor(context, R.color.severity_orange)) // orange
                SeverityLevel.EXTREME -> binding.textSeverity.setTextColor(ContextCompat.getColor(context, R.color.severity_red))
                SeverityLevel.UNKNOWN -> binding.textSeverity.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
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