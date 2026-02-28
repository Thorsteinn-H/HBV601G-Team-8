package `is`.hi.hbv601g.icelandicweatherapp.data

data class ForecastDto(
    val station: Int,
    val name: String,
    val time: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val t: Double, //Hitastig °C
    val tx: Double, //Hámrkshiti °C
    val tn: Double, //Lágmarkshit °C
    val rh: Int, //rakastig %
    val vp: Double, //Eimþrýstingur hPa
    val td: Double, //Daggarmark °C
    val f: Double, //Vindhraði m/s
    val fx: Double, //Hámarksindhraði m/s
    val fg: Double, //Vindhviða m/s
    val d: Int    //Vindátt 360 = Norðanátt, 90 = Austanátt, 180 = Sunnanátt, 270 = Vestanátt, gráður"
)