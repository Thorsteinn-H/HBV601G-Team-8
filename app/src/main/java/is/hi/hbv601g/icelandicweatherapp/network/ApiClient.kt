package `is`.hi.hbv601g.icelandicweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
}