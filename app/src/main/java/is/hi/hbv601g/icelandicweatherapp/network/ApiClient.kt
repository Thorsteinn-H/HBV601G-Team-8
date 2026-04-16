package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.CloudResponse
import `is`.hi.hbv601g.icelandicweatherapp.data.OpenMeteoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines the API base URL
 * Configures JSON parsing (Gson)
 * Exposes the VedurApi interface
 */
object ApiClient {

    /**
     * Base URL = https://api.vedur.is/cap/v1/
     *
     */
    private const val BASE_URL = "https://api.vedur.is/cap/v1/"

    /**
     * lazy means its not created until api is accesssed the first time
     *  - saves memory and startup
     */
    val api: VedurApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //converter for JSON into Kotlin data classes
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VedurApi::class.java)
    }
    private const val NORTHERN_LIGHTS_BASE_URL = "https://services.swpc.noaa.gov/"

    private val northernLightsRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NORTHERN_LIGHTS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val northernLightsApi: NorthernLightsApi by lazy {
        northernLightsRetrofit.create(NorthernLightsApi::class.java)
    }

    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val weatherRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val weatherApi: WeatherApi by lazy {
        weatherRetrofit.create(WeatherApi::class.java)
    }

    private const val OPEN_METEO_BASE_URL = "https://api.open-meteo.com/v1/"
    private val openMeteoRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(OPEN_METEO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val openMeteoApi: OpenMeteoApi by lazy {
        openMeteoRetrofit.create(OpenMeteoApi::class.java)
    }
}

interface WeatherApi {
    @GET("forecast")
    suspend fun getCloudForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): CloudResponse
}

interface OpenMeteoApi {
    @GET("forecast")
    suspend fun getDetailedClouds(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("hourly") hourly: String,
        @Query("forecast_days") days: Int
    ): OpenMeteoResponse
}
