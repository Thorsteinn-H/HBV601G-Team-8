package `is`.hi.hbv601g.icelandicweatherapp.ui.attractions

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentNorthernLightsBinding

class NorthernLightsFragment : Fragment() {

    private var _binding: FragmentNorthernLightsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNorthernLightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}