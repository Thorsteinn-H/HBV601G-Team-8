package `is`.hi.hbv601g.icelandicweatherapp.data

data class CloudResponse(
    val list: List<CloudForecast>
)

data class CloudForecast(
    val dt: Long,
    val clouds: Clouds,
    val main: MainTemp
)

data class Clouds(
    val all: Int // Cloudiness %
)

data class MainTemp(
    val temp: Double
)

data class OpenMeteoResponse(
    val hourly: OpenMeteoHourly
)

data class OpenMeteoHourly(
    val time: List<String>,
    val cloud_cover: List<Int>,
    val cloud_cover_low: List<Int>,
    val cloud_cover_mid: List<Int>,
    val cloud_cover_high: List<Int>
)
