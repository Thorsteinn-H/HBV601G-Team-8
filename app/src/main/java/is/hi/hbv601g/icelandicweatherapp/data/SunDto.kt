package `is`.hi.hbv601g.icelandicweatherapp.data


data class SunDto (
    val results: result, //Gögn eru inn í þess var nested þ.a. þarf auka data class
    val status: String, //Ef það gekk
    val tzid: String  //Timezone

    )

data class result(
    val sunrise:String,
    val sunset:String,
    // not used but hany if we want
    // time when the sun is at its highest
    val solar_noon:String,
    // total time the sun is out
    val day_length:String,
    // when daylight is enough for activities?
    val civil_twilight_begin:String,
    // when it ends
    val civil_twilight_end:String,
    //horizon stil visible at sea
    val nautical_twilight_begin:String,
    // end of it
    val nautical_twilight_end:String,
    // start of sky begining to get light
    val astronomical_twilight_begin:String,
    // end of it
    val astronomical_twilight_end: String
)

