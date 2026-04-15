package `is`.hi.hbv601g.icelandicweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Defines the API base URL
 * Configures JSON parsing (Gson)
 * Exposes the VedurApi interface
 */
object VedurApiClient {

    /**
     * Base URL = https://api.vedur.is/cap/v1/
     *
     */
    private const val BASE_URL = "https://api.vedur.is"


    /**
     * lazy means its not created until api is accesssed the first time
     *  - saves memory and startup
     */
    val api: VedurAlertsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //converter for JSON into Kotlin data classes
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VedurAlertsApi::class.java)
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


private const val OPEN_METEO_BASE_URL = "https://api.open-meteo.com/"

private val glacierForecastRetrofit by lazy {
    Retrofit.Builder()
        .baseUrl(OPEN_METEO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val glacierForecastApi: GlacierForecastApi by lazy {
    glacierForecastRetrofit.create(GlacierForecastApi::class.java)
}
}