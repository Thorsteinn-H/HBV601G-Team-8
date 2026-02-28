package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentLocationsBinding
import kotlin.getValue

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LocationsViewModel by viewModels()

    private lateinit var locationsAdapter: LocationsAdapter

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

        viewModel.forecast.observe(viewLifecycleOwner) { list ->
            Log.d("LocationsFragment", "Forecast list size: ${list.size}")
            locationsAdapter.updateList(list)
        }

        viewModel.loadForecasts(1,"2025-01-02","2025-01-02")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}