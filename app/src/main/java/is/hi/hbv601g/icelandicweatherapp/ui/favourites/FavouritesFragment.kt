package `is`.hi.hbv601g.icelandicweatherapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import `is`.hi.hbv601g.icelandicweatherapp.databinding.FragmentFavouritesBinding

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
class
FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouritesViewModel by viewModels()

    private lateinit var favouritesAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Trigger API call
        favouritesAdapter = FavouritesAdapter()

        binding.recyclerviewFavourites.apply {
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.adapter = favouritesAdapter
        }

        viewModel.alerts.observe(viewLifecycleOwner){ alerts ->
            favouritesAdapter.submitList(alerts)
        }

        viewModel.loadAlerts()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}