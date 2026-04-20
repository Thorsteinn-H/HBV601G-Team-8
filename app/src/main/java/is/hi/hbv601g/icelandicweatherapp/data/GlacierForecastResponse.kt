package `is`.hi.hbv601g.icelandicweatherapp.data

data class GlacierForecastResponse(
    val current: CurrentWeatherDto,
    val daily: DailyWeatherDto
)

data class CurrentWeatherDto(
    val temperature_2m: Double,
    val wind_speed_10m: Double,
    val weather_code: Int
)

data class DailyWeatherDto(
    val time: List<String>,
    val weather_code: List<Int>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val precipitation_sum: List<Double>,
    val snowfall_sum: List<Double>
)