package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentWeatherAlertsBinding

/**
 * Fragment responsible for displaying weather alerts
 */
class WeatherAlertsFragment : Fragment() {

    // nullable to avoid memory leaks
    private var _binding: FragmentWeatherAlertsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //ViewModel that provides alert data
    private val viewModel : WeatherAlertsViewModel by viewModels()

    //RecyclerView adapter for displaying alerts
    private lateinit var adapter: WeatherAlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // initialize RecyclerView
        setupRecyclerView()

        viewModel.alerts.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                android.widget.Toast.makeText(requireContext(), it, android.widget.Toast.LENGTH_LONG).show()
            }
        }

        //trigger loading of alerts
        viewModel.loadAlerts()
    }


    private fun setupRecyclerView() {
        adapter = WeatherAlertsAdapter()

        binding.recyclerViewAlerts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@WeatherAlertsFragment.adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}