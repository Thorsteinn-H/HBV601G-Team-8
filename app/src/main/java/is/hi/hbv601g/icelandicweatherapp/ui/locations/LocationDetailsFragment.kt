package `is`.hi.hbv601g.icelandicweatherapp.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentLocationsBinding

/**
 * fragemnt responsible for displaying the full forecast for a selected location
 *
 * Recieves location name + coordinates, loads forecast from ViewModel
 */
class LocationDetailsFragment : Fragment() {

    //ViewBinding reference
    private var _binding: FragmentLocationsBinding? = null
    //binding accessor
    private val binding get() = _binding!!

    //ViewModel responsible for loading forecast data
    private val viewModel: LocationDetailsViewModel by viewModels()

    //RecyclerView adapter for displaying the forecast list
    private lateinit var adapter : ForecastAdapter


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

        // retrieve location name passsed from the previous screen
        val locationName =
            requireArguments().getString("locationName")
                ?: error("locationName missing")

        val latitude =
            requireArguments().getFloat("latitude")

        val longitude =
            requireArguments().getFloat("longitude")

        //set the activity title to the selected location
        requireActivity().title = locationName
        //initialize recyclerView
        setupRecyclerView()

        // Request forecast data for the location
        viewModel.loadForecast(
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble()
        )

        observeViewModel()
    }

    /**
     * configures the recyclerView and attache the ForecastAdapter
     */
    private fun setupRecyclerView(){
        adapter = ForecastAdapter()

        binding.recyclerViewLocations.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@LocationDetailsFragment.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.forecasts.observe(viewLifecycleOwner){ forecasts ->
            adapter.submitList(forecasts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}