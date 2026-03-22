package `is`.hi.hbv601g.icelandicweatherapp.ui.northernlights

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.data.NorthernLightsCurrentDto
import kotlin.math.abs

class NorthernLightsFragment : Fragment(R.layout.fragment_northern_lights) {

    private lateinit var viewModel: NorthernLightsViewModel
    private lateinit var textView: TextView
    private lateinit var spinner: Spinner

    private var latestData: NorthernLightsCurrentDto? = null

    private val towns = mapOf(
        "Reykjavík" to Pair(64.1466, -21.9426),
        "Akureyri" to Pair(65.6885, -18.1262),
        "Ísafjörður" to Pair(66.0748, -23.1340),
        "Egilsstaðir" to Pair(65.2669, -14.3948)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NorthernLightsViewModel::class.java]

        textView = view.findViewById(R.id.textNorthernLights)
        spinner = view.findViewById(R.id.townSpinner)

        val townNames = towns.keys.toList()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            townNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        textView.text = "Loading Northern Lights data..."

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateForecastForSelectedTown()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.currentNorthernLights.observe(viewLifecycleOwner) { data ->
            latestData = data
            updateForecastForSelectedTown()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            textView.text = error
        }

        viewModel.loadCurrentNorthernLights()
    }

    private fun updateForecastForSelectedTown() {
        val data = latestData ?: return

        val selectedTown = spinner.selectedItem?.toString() ?: return
        val townCoords = towns[selectedTown] ?: return

        val closestPoint = data.coordinates.minByOrNull { point ->
            val lat = point[0]
            val lon = point[1]
            abs(lat - townCoords.first) + abs(lon - townCoords.second)
        }

        if (closestPoint == null || closestPoint.size < 3) {
            textView.text = "No Northern Lights data available."
            return
        }

        val activityValue = closestPoint[2]

        val activityLevel = when {
            activityValue < 10 -> "Low"
            activityValue < 30 -> "Moderate"
            activityValue < 60 -> "High"
            else -> "Very High"
        }

        textView.text = """
            Northern Lights Forecast
            
            Town: $selectedTown
            Status: $activityLevel
            Activity value: $activityValue
        """.trimIndent()
    }
}