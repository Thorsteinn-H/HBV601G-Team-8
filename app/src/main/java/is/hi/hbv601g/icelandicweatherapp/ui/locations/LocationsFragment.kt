package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import  `is`.hi.hbv601g.icelandicweatherapp.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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

    //client used to access the devices last known location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * handles the permession request for location access
     *
     * if location is granted then fetch current location
     * if denied log an error
     */
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                // Permission denied
                Log.e("LOCATION", "Permission denied")
            }
        }

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

        //initialize the fusedlocationclient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //trigger loading of weather data for all locations starting with current
        getCurrentLocation()
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
        //check if permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted, safe to access location
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    viewModel.loadAllWeatherWithUserLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }
            }

        } else {
            // Permission not granted, request it
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}