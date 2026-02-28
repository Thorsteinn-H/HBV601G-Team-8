package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentLocationsBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.getValue

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LocationsViewModel by viewModels()

    private lateinit var locationsAdapter: LocationsAdapter

    private val regions = listOf("Höfuðborgarsvæðið" to 1, "Suðurland" to 2, "Faxaflói" to 3,
        "Breiðafjörður" to 4, "Vestfirðir" to 5, "Strandir og norðurland vestra" to 6, "Norðurland eystra" to 7,
        "Austurland að Glettingi" to 8, "Austfirðir" to 9, "Suðausturland" to 10, "Miðhálendi" to 11)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        locationsAdapter= LocationsAdapter(emptyList())

        binding.recyclerForecast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationsAdapter
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            regions.map { it.first }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRegion.adapter = adapter

        binding.spinnerRegion.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val regionId = regions[position].second
                val todayString = getToday()
                viewModel.loadForecasts(regionId, todayString, todayString)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Þarf að  hafa
            }
        })

        viewModel.forecast.observe(viewLifecycleOwner) { list ->
            locationsAdapter.updateList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getToday(): String {
        val date = Calendar.getInstance()
        val form = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayString = form.format(date.time)
        return todayString;

    }
}