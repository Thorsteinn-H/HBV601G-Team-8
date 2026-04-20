package `is`.hi.hbv601g.icelandicweatherapp.ui.glaciers

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import `is`.hi.hbv601g.icelandicweatherapp.R
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient
import `is`.hi.hbv601g.icelandicweatherapp.utilities.TimeUtils
import `is`.hi.hbv601g.icelandicweatherapp.utilities.WeatherUtils
import kotlinx.coroutines.launch
import java.util.Locale

class GlaciersFragment : Fragment(R.layout.fragment_glaciers) {

    private lateinit var textView: TextView
    private lateinit var titleView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById(R.id.textGlaciers)
        titleView = view.findViewById(R.id.glacierTitle)

        val btnSnaefellsjokull: Button = view.findViewById(R.id.btnSnaefellsjokull)
        val btnLangjokull: Button = view.findViewById(R.id.btnLangjokull)
        val btnHofsjokull: Button = view.findViewById(R.id.btnHofsjokull)
        val btnDrangajokull: Button = view.findViewById(R.id.btnDrangajokull)
        val btnEyjafjallajokull: Button = view.findViewById(R.id.btnEyjafjallajokull)
        val btnMyrdalsjokull: Button = view.findViewById(R.id.btnMyrdalsjokull)
        val btnVatnajokull: Button = view.findViewById(R.id.btnVatnajokull)

        btnSnaefellsjokull.setOnClickListener { loadGlacierForecast("Snæfellsjökull") }
        btnLangjokull.setOnClickListener { loadGlacierForecast("Langjökull") }
        btnHofsjokull.setOnClickListener { loadGlacierForecast("Hofsjökull") }
        btnDrangajokull.setOnClickListener { loadGlacierForecast("Drangajökull") }
        btnEyjafjallajokull.setOnClickListener { loadGlacierForecast("Eyjafjallajökull") }
        btnMyrdalsjokull.setOnClickListener { loadGlacierForecast("Mýrdalsjökull") }
        btnVatnajokull.setOnClickListener { loadGlacierForecast("Vatnajökull") }
    }

    private fun loadGlacierForecast(glacierName: String) {
        val glacier = GlacierRegionMapper.glacierLocations[glacierName]

        val hitastigStilling = WeatherUtils.readHitastigSettings(requireContext())


        if (glacier == null) {
            textView.text = "Fann ekki staðsetningu fyrir $glacierName."
            return
        }

        titleView.text = glacierName
        textView.text = "Hleð 3 daga spá fyrir $glacierName..."

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = VedurApiClient.glacierForecastApi.getForecast(
                    latitude = glacier.latitude,
                    longitude = glacier.longitude,
                    elevation = glacier.elevation,
                    forecastDays = 3
                )

                val currentTemp = response.current.temperature_2m
                val currentWind = response.current.wind_speed_10m
                val currentWeather = weatherCodeToText(response.current.weather_code)

                val forecastText = buildString {
                    appendLine("3 daga jöklaspá")
                    appendLine()
                    appendLine("Jökull: ${glacier.name}")
                    appendLine()
                    appendLine("Núna:")
                    if(hitastigStilling==1){
                        appendLine("• Hiti: ${format1(currentTemp)} °C")
                    }
                    else{
                        val fahrenheit= WeatherUtils.calculateFahrenheit(currentTemp)
                        appendLine("• Hiti: ${format1(fahrenheit)} °F")
                    }

                    appendLine("• Vindur: ${format1(currentWind)} m/s")
                    appendLine("• Aðstæður: $currentWeather")
                    appendLine()

                    for (i in 0 until minOf(3, response.daily.time.size)) {
                        val date = TimeUtils.fancyStringFormatDate(response.daily.time[i])
                        val maxTemp = response.daily.temperature_2m_max.getOrNull(i)
                        val minTemp = response.daily.temperature_2m_min.getOrNull(i)
                        val precipitation = response.daily.precipitation_sum.getOrNull(i)
                        val snowfall = response.daily.snowfall_sum.getOrNull(i)
                        val weatherCode = response.daily.weather_code.getOrNull(i) ?: -1

                        appendLine("Dagur ${i + 1} - $date")
                        appendLine("• Veður: ${weatherCodeToText(weatherCode)}")
                        if(hitastigStilling==1){
                            appendLine("• Hæsti hiti: ${format1(maxTemp)} °C")
                            appendLine("• Lægsti hiti: ${format1(minTemp)} °C")
                        }
                        else{
                            val fahrenheitMax= WeatherUtils.calculateFahrenheit(maxTemp?: 0.0)
                            appendLine("• Hæsti hiti: ${format1(fahrenheitMax)} °F")
                            val fahrenheitMin= WeatherUtils.calculateFahrenheit(minTemp?: 0.0)
                            appendLine("• Lægsti hiti: ${format1(fahrenheitMin)} °F")
                        }


                        appendLine("• Úrkoma: ${format1(precipitation)} mm")
                        appendLine("• Snjókoma: ${format1(snowfall)} cm")
                        appendLine()
                    }
                }

                textView.text = forecastText.trim()

            } catch (e: Exception) {
                textView.text = """
                Tókst ekki að sækja 3 daga spá fyrir $glacierName.

                Villa:
                ${e.message ?: "Óþekkt villa"}
            """.trimIndent()
            }
        }
    }

    private fun weatherCodeToText(code: Int): String {
        return when (code) {
            0 -> "Heiðskírt"
            1, 2, 3 -> "Skýjað"
            45, 48 -> "Þoka"
            51, 53, 55 -> "Úði"
            61, 63, 65 -> "Rigning"
            66, 67 -> "Krapi eða ísing"
            71, 73, 75 -> "Snjókoma"
            77 -> "Snjókorn"
            80, 81, 82 -> "Skúrir"
            85, 86 -> "Snjóél"
            95 -> "Þrumuveður"
            96, 99 -> "Þrumuveður með hagli"
            else -> "Óþekkt"
        }
    }

    private fun format1(value: Double?): String {
        return if (value == null) {
            "-"
        } else {
            String.format(Locale.US, "%.1f", value)
        }
    }
}