package `is`.hi.hbv601g.icelandicweatherapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  object responsible for configuring and providing the client used to
 *   communicate with the met.no API
 */
object MetApiClient {

    /**
     * base url for met.no =  https://api.met.no/weatherapi/locationforecast/2.0/
     */
    private const val BASE_URL =
        "https://api.met.no/weatherapi/locationforecast/2.0/"

    /**
     * Met.no asks for a User-Agent-Requirement, this sattisfies that for us
     * The incerceptor automatically adds this header to every API request
     *      OkHttpClient lives HERE
      */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header(
                    "User-Agent",
                    "IcelandicWeatherApp/1.0 (ams59@hi.is)"
                )
                .build()

            // continue the request with the header
            chain.proceed(request)
        }
        .build()

    /**
     * Retrofit uses the OkHttpClient
     * by lazy ensures the api client is only created once
      */
    val api: MetForcastApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetForcastApi::class.java)
    }
}