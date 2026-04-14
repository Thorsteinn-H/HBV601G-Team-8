package `is`.hi.hbv601g.icelandicweatherapp.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.ContentProviderCompat.requireContext
import kotlin.math.exp
import androidx.core.content.edit

object WeatherUtils {
    /**
     * Calculates the apparent temperature ("feels like") based on the formula:
     * AT = Ta + 0.33 * e - 0.7 * v - 4.00
     *
     * where:
     * Ta = air temperature (°C)
     * e = water vapour pressure (hPa)
     * v = wind speed (m/s)
     *
     * Vapour pressure e is calculated as:
     * e = (RH / 100) * 6.105 * exp((17.27 * Ta) / (237.7 + Ta))
     *
     * @param temperature Dry-bulb temperature in Celsius
     * @param windSpeed Wind speed in m/s
     * @param relativeHumidity Relative humidity in %
     * @return Apparent temperature in Celsius, or null if any input is null
     */
    fun calculateFeelsLike(
        temperature: Double?,
        windSpeed: Double?,
        relativeHumidity: Double?
    ): Double? {
        if (temperature == null || windSpeed == null || relativeHumidity == null) {
            return null
        }

        val e = (relativeHumidity / 100.0) * 6.105 * exp((17.27 * temperature) / (237.7 + temperature))
        return temperature + (0.33 * e) - (0.7 * windSpeed) - 4.00
    }

    fun calculateFahrenheit(celcius: Double): Double {
        return (celcius * (9.0/5.0)) + 32

    }

    fun readHitastigSettings(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences("UserPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("hitastig",1)

    }

    fun geymaHitastig(context: Context, id:Int){
        val sharedPref = context.getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val editor = sharedPref.edit()

        if(id==1){
            editor.putInt("hitastig",1)
            editor.apply()

        } else if (id==2){
            editor.putInt("hitastig",2)
            editor.apply()

        }
    }

    fun geymaFavorite(context: Context, nafn:String){
        val sharedPref = context.getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val favorites = sharedPref.getStringSet("favorites",emptySet())

        val listi = favorites?.toMutableSet()

        listi?.add(nafn)

        sharedPref.edit { putStringSet("favorites", listi) }


    }

    fun eyðaFavorite(context: Context, nafn:String){
        val sharedPref = context.getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val favorites = sharedPref.getStringSet("favorites",emptySet())

        val listi = favorites?.toMutableSet()

        listi?.remove(nafn)

        sharedPref.edit { putStringSet("favorites", listi) }

    }

    fun getFavorite(context: Context): MutableSet<String?>? {
        val sharedPref = context.getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val favorites = sharedPref.getStringSet("favorites",emptySet())

        val listi = favorites?.toMutableSet()

        return listi


    }

}
