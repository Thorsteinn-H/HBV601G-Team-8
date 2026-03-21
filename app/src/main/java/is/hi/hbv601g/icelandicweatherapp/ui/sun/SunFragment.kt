package `is`.hi.hbv601g.icelandicweatherapp.ui.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentSunBinding
import kotlin.getValue

class SunFragment: Fragment() {

    private var _binding: FragmentSunBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SunViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSunBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ætti að vera rétt fyrir ísland
        viewModel.loadSun(64.1355,21.8954)


        viewModel.sun.observe(viewLifecycleOwner) { data ->
            binding.textSunRise.text=data.results.sunrise
            binding.textSunSet.text=data.results.sunset
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}