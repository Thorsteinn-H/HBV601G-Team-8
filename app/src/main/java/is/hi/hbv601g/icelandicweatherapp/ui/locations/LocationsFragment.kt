package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import  `is`.hi.hbv601g.icelandicweatherapp.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentLocationsBinding
import `is`.hi.hbv601g.icelandicweatherapp.model.IcelandLocations
import kotlin.getValue

/**
 * Fragment responsible for displaying the list of locations
 *
 * observesa LocationsViewModel
 * Handles navigation to LocationDetailsFragment when a location is clicked
 */
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
        //adapter with click callback
        locationsAdapter = LocationsAdapter{ selectedItem ->
            //find the full location object by matching name
            val location = IcelandLocations.majorIcelandLocation.first{
                it.name == selectedItem.locationName
            }
            // create a bundle to pass data
            val bundle = bundleOf(
                    "locationName" to location.name,
                    "latitude" to location.latitude.toFloat(),
                    "longitude" to location.longitude.toFloat()
                )
            findNavController().navigate(
                R.id.locationDetailsFragment,
                bundle
            )
        }

        binding.recyclerViewLocations.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationsAdapter
        }
    }

    /**
     * currently unused, could be used instead of observing inline
     */
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