package `is`.hi.hbv601g.icelandicweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RoadApiClient {

    // https://gagnaveita.vegagerdin.is/api/faerd2017_1
    private const val BASE_URL =
        "https://gagnaveita.vegagerdin.is/api/faerd2017_1"

    val api: RoadApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(RoadApi::class.java)
    }

}