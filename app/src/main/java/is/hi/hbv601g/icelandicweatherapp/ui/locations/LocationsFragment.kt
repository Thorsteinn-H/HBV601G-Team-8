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

    private lateinit var locationsAdapter: LocationsAdapter

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
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            locationsAdapter.submitList(it)
        }

        viewModel.loadCurrentWeatherForAllLocations()
    }

    private fun setupRecyclerView(){
        locationsAdapter = LocationsAdapter()

        binding.recyclerViewLocations.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationsAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.currentWeather.observe(viewLifecycleOwner){ items ->
            locationsAdapter.submitList(items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}