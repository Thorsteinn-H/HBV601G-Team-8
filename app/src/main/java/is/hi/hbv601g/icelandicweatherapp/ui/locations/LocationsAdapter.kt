package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemLocationsBinding

class LocationsAdapter(private var forecast: List<ForecastDto>) :
    RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {
    private val expanded=mutableListOf<Int>()
    class LocationsViewHolder(val  view: ItemLocationsBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LocationsViewHolder {
        val view = ItemLocationsBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false)

        return LocationsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: LocationsViewHolder, position: Int) {
        val forecast = forecast[position]
        viewHolder.view.textName.text=forecast.name
        viewHolder.view.textTemp.text="Hitastig: " + forecast.t.toString()+ " °C"
        viewHolder.view.textHumidity.text="Raki: " + forecast.rh.toString()+" %"
        viewHolder.view.textMaxHeat.text="Hæðsti hiti: " + forecast.tx.toString()+" °C"
        viewHolder.view.textMinHeat.text="Minnsti hiti: " +forecast.tn.toString()+" °C"
        viewHolder.view.textWind.text="Vindhraði: " +forecast.f.toString()+" °m/s"
        viewHolder.view.textWindDir.text="Vindátt: " +forecast.d.toString()+" °"

        if(expanded.contains(position)){
            viewHolder.view.textTemp.visibility=View.VISIBLE
            viewHolder.view.textHumidity.visibility=View.VISIBLE
            viewHolder.view.textMaxHeat.visibility=View.VISIBLE
            viewHolder.view.textMinHeat.visibility=View.VISIBLE
            viewHolder.view.textWind.visibility=View.VISIBLE
            viewHolder.view.textWindDir.visibility=View.VISIBLE
        } else{
            viewHolder.view.textTemp.visibility=View.GONE
            viewHolder.view.textHumidity.visibility=View.GONE
            viewHolder.view.textMaxHeat.visibility=View.GONE
            viewHolder.view.textMinHeat.visibility=View.GONE
            viewHolder.view.textWind.visibility=View.GONE
            viewHolder.view.textWindDir.visibility=View.GONE
        }

        viewHolder.view.root.setOnClickListener {
            if(expanded.contains(position)){
                expanded.remove(position)
            } else {
                expanded.add(position)
            }
            notifyItemChanged(position)
        }


    }

    override fun getItemCount() = forecast.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ForecastDto>) {
        forecast=list
        notifyDataSetChanged()
    }

}