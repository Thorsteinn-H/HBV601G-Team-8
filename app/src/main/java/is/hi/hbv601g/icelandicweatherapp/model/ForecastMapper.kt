package `is`.hi.hbv601g.icelandicweatherapp.model

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ForecastResponse

/**
 * maps met.no into a list of ForecastDto
 */
fun ForecastResponse.toForecastDtos(): List<ForecastDto> {

    // iterate through each forecast time series
    return properties.timeseries.map { series ->

        // weather details that are always available
        val instantDetails = series.data.instant.details
        ForecastDto(
            // timestamp
            time = series.time,
            //always available
            temperature = instantDetails.airTemperature,
            windSpeed = instantDetails.windSpeed,

            //optional
            precipitation = series.data.next1Hours?.details?.precipitationAmount,
            symbolCode = series.data.next1Hours?.summary?.symbolCode
        )
    }
}