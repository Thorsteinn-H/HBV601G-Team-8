package `is`.hi.hbv601g.icelandicweatherapp.ui.transform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentTransformBinding
import `is`.hi.hbv601g.icelandicweatherapp.databinding.ItemTransformBinding
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentSlideshowBinding

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
class
TransformFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransformViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Trigger API call
        viewModel.loadAlerts()
        // observe liveData from viewModel
        viewModel.alerts.observe(viewLifecycleOwner){ alerts ->
            binding.textSlideshow.text =
                if(alerts.isNotEmpty()){
                    alerts[0].headline ?: "No headline"
                } else {
                    "no active alerts"
                }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}