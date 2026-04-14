package `is`.hi.hbv601g.icelandicweatherapp.ui.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.telephony.RadioAccessSpecifier
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentEarthquakesBinding
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentSettingsBinding
import `is`.hi.hbv601g.icelandicweatherapp.model.IcelandLocations
import `is`.hi.hbv601g.icelandicweatherapp.model.WeatherUtils

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = binding.hitastigStillingar

        val context = binding.root.context

        val valid = WeatherUtils.readHitastigSettings(context)

        val locations = IcelandLocations.majorIcelandLocation

        val linearLayout = binding.layout

        val favorites= WeatherUtils.getFavorite(context)

        for (location in locations) {
            val box = CheckBox(context)
            box.text=location.name

            if(favorites?.contains(location.name) ?: false){
                box.isChecked=true
            }

            box.setOnCheckedChangeListener { _, bool ->
                if(bool){
                    WeatherUtils.geymaFavorite(context,location.name)

                } else{
                    WeatherUtils.eyðaFavorite(context,location.name)
                }
            }
            linearLayout.addView(box)
        }

        when(valid){
            1 -> {radioGroup.check(binding.celcius.id)}
            2 -> {radioGroup.check(binding.fahrenheit.id)}
        }

        radioGroup.setOnCheckedChangeListener { _, i ->
            when(i) {
                binding.celcius.id->{
                    WeatherUtils.geymaHitastig(context,1)

                }
                binding.fahrenheit.id->{
                    WeatherUtils.geymaHitastig(context,2)

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}