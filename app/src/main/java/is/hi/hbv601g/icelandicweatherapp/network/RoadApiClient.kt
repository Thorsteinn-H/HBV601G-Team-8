package `is`.hi.hbv601g.icelandicweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

//used to make network requests to vegagerðin ARCGIS API
object RoadApiClient {

    // BASE_URL
    //https://vegasja.vegagerdin.is/
    private const val BASE_URL =
        "https://vegasja.vegagerdin.is/"

    //Lazy so its only made once
    val api: RoadApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RoadApi::class.java)
    }

}