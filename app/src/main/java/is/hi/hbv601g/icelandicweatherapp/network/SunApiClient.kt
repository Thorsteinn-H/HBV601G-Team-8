package `is`.hi.hbv601g.icelandicweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Defines the API base URL
 * Configures JSON parsing (Gson)
 * Exposes the Sun interface
 */
object SunApiClient {

    /**
     * Sunset URL = https://api.sunrise-sunset.org
     *
     */
    private const val BASE_URL = "https://api.sunrise-sunset.org"

    /**
     * lazy means its not created until api is accesssed the first time
     *  - saves memory and startup
     */
    val sunApi: SunApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //converter for JSON into Kotlin data classes
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SunApi::class.java)

    }
}