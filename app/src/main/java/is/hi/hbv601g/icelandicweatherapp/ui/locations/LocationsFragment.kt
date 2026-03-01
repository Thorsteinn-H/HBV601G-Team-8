package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentLocationsBinding
import kotlin.getValue

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LocationsViewModel by viewModels {
        LocationsViewModelFactory(requireActivity().application)
    }

    private lateinit var forecastAdapter: LocationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        Log.e("TEST", "calling loadForecast()")
        viewModel.loadForecasts(
            latitude = 64.1466,
            longitude = -21.9426
        )
    }

    private fun setupRecyclerView(){
        forecastAdapter = LocationsAdapter()

        binding.recyclerViewLocations.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = forecastAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.forecasts.observe(viewLifecycleOwner){ forecasts ->
            forecastAdapter.submitList(forecasts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}