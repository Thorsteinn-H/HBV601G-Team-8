package `is`.hi.hbv601g.icelandicweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VedurCapRetrofitInstance {

    val api: VedurCapApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.vedur.is/cap/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VedurCapApiService::class.java)
    }
}