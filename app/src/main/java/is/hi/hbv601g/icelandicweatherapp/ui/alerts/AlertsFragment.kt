package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentAlertsBinding

/**
 * Fragment responsible for displaying weather alerts
 */
class AlertsFragment : Fragment() {

    // nullable to avoid memory leaks
    private var _binding: FragmentAlertsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //ViewModel that provides alert data
    private val viewModel : AlertsViewModel by viewModels()

    //RecyclerView adapter for displaying alerts
    private lateinit var adapter: AlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // initialize RecyclerView
        setupRecyclerView()

        viewModel.alerts.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        //trigger loading of alerts
        viewModel.loadAlerts()
    }


    private fun setupRecyclerView() {
        adapter = AlertsAdapter()

        binding.recyclerViewAlerts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AlertsFragment.adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}