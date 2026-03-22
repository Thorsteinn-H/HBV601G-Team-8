package `is`.hi.hbv601g.icelandicweatherapp.ui.glaciers

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurCapRetrofitInstance
import kotlinx.coroutines.launch

class GlaciersFragment : Fragment(R.layout.fragment_glaciers) {

    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById(R.id.textGlaciers)

        val btnSnaefellsjokull: Button = view.findViewById(R.id.btnSnaefellsjokull)
        val btnLangjokull: Button = view.findViewById(R.id.btnLangjokull)
        val btnHofsjokull: Button = view.findViewById(R.id.btnHofsjokull)
        val btnDrangajokull: Button = view.findViewById(R.id.btnDrangajokull)
        val btnEyjafjallajokull: Button = view.findViewById(R.id.btnEyjafjallajokull)
        val btnMyrdalsjokull: Button = view.findViewById(R.id.btnMyrdalsjokull)
        val btnVatnajokull: Button = view.findViewById(R.id.btnVatnajokull)

        btnSnaefellsjokull.setOnClickListener {
            loadGlacierForecast("Snæfellsjökull")
        }

        btnLangjokull.setOnClickListener {
            loadGlacierForecast("Langjökull")
        }

        btnHofsjokull.setOnClickListener {
            loadGlacierForecast("Hofsjökull")
        }

        btnDrangajokull.setOnClickListener {
            loadGlacierForecast("Drangajökull")
        }

        btnEyjafjallajokull.setOnClickListener {
            loadGlacierForecast("Eyjafjallajökull")
        }

        btnMyrdalsjokull.setOnClickListener {
            loadGlacierForecast("Mýrdalsjökull")
        }

        btnVatnajokull.setOnClickListener {
            loadGlacierForecast("Vatnajökull")
        }
    }

    private fun loadGlacierForecast(glacierName: String) {
        val areaId = GlacierRegionMapper.glacierToAreaId[glacierName]

        if (areaId == null) {
            textView.text = "No Veður region mapping found for $glacierName."
            return
        }

        textView.text = "Loading forecast for $glacierName..."

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val region = VedurCapRetrofitInstance.api.getForecastRegion(areaId)

                val message = when (glacierName) {
                    "Drangajökull" -> "Remote glacier area in the Westfjords. Check warnings carefully."
                    "Snæfellsjökull" -> "Western glacier. Usually accessible but watch wind."
                    "Langjökull" -> "Large glacier with strong wind exposure."
                    "Hofsjökull" -> "Highland glacier. Conditions can change quickly."
                    "Eyjafjallajökull" -> "South coast glacier. Watch for wind and clouds."
                    "Mýrdalsjökull" -> "Often cloudy and icy. Be cautious."
                    "Vatnajökull" -> "Massive glacier in southeast Iceland. Conditions vary a lot."
                    else -> "Glacier conditions available."
                }

                textView.text = """
                    Glacier Forecast

                    Glacier: $glacierName
                    Veður region: ${region.name ?: "Unknown"}
                    Region ID: ${region.id}

                    Status:
                    $message

                    Source:
                    Veður API (CAP)
                """.trimIndent()

            } catch (e: Exception) {
                textView.text = """
                    Glacier Forecast

                    Glacier: $glacierName

                    Failed to load data from Veður API.
                    ${e.message}
                """.trimIndent()
            }
        }
    }
}