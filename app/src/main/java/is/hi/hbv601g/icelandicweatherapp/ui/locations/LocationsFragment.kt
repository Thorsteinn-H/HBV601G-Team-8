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
import com.google.android.gms.location.FusedLocationProviderClient
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentLocationsBinding
import `is`.hi.hbv601g.icelandicweatherapp.model.IcelandLocations
import kotlin.getValue

/**
 * Fragment responsible for displaying the list of locations
 *
 * observes LocationsViewModel
 * Handles navigation to LocationDetailsFragment when a location is clicked
 */
class LocationsFragment : Fragment() {

    //ViewBinding reference
    private var _binding: FragmentLocationsBinding? = null

    //accessor for binding
    private val binding get() = _binding!!

    // ViewModel used to load weather data for locations
    private val viewModel: LocationsViewModel by viewModels {
        LocationsViewModelFactory(requireActivity().application)
    }

    // RecyclerView adapter that displays the list of locations
    private lateinit var locationsAdapter: LocationsAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

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

        //trigger loading of weather data for all locations
        viewModel.loadCurrentWeatherForAllLocations()
    }

    /**
     * configures the RecyclerView and handles click events on locations
     */
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
            //navigate th  LocationsDetailFragment with selected location
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

    /**
     *  get current location using gps tracker on phone
     */
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModel.loadCurrentWeatherWithUserLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}