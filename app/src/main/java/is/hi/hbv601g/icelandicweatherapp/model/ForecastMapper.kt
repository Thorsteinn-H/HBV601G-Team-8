package `is`.hi.hbv601g.icelandicweatherapp.model

import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ForecastResponse

/**
 * maps met.no into a list of ForecastDto
 */
fun ForecastResponse.toForecastDtos(): List<ForecastDto> {
    return properties.timeseries.map { series ->
        ForecastDto(
            time = series.time,
            temperature = series.data.instant.details.airTemperature,
            windSpeed = series.data.instant.details.windSpeed,
            precipitation = series.data.next1Hours?.details?.precipitationAmount,
            symbolCode = series.data.next1Hours?.summary?.symbolCode
        )
    }

}