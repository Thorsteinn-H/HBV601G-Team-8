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

class LocationDetailsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!


    private val viewModel: LocationDetailsViewModel by viewModels()
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

        val locationName =
            requireArguments().getString("locationName")
                ?: error("locationName missing")

        val latitude =
            requireArguments().getFloat("latitude")

        val longitude =
            requireArguments().getFloat("longitude")

        requireActivity().title = locationName
        setupRecyclerView()

        viewModel.loadForecast(
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble()
        )

        observeViewModel()
    }

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