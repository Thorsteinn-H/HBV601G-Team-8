package `is`.hi.hbv601g.icelandicweatherapp.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import `is`.hi.hbv601g.icelandicweatherapp.R
import androidx.navigation.fragment.findNavController
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentAlertsMenuBinding


class AlertsMenuFragment : Fragment() {

    private var _binding: FragmentAlertsMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlertsMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.cardVedur.setOnClickListener {
            findNavController().navigate(R.id.weatherAlertsFragment)
        }

        binding.cardVolcano.setOnClickListener {
            // TODO later
        }

        binding.cardEarthquake.setOnClickListener {
            // TODO later
        }

        binding.cardRoad.setOnClickListener {
            // TODO later
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}