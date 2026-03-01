package `is`.hi.hbv601g.icelandicweatherapp.data


data class SunDto (
    val results: result, //Gögn eru inn í þess var nested þ.a. þarf auka data class
    val status: String, //Ef það gekk
    val tzid: String  //Timezone

    )

data class result(
    val sunrise:String,
    val sunset:String,
    val solar_noon:String,
    val day_length:String,
    val civil_twilight_begin:String,
    val civil_twilight_end:String,
    val nautical_twilight_begin:String,
    val nautical_twilight_end:String,
    val astronomical_twilight_begin:String,
    val astronomical_twilight_end: String
)

