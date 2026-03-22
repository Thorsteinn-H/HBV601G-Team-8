package `is`.hi.hbv601g.icelandicweatherapp.network

import com.google.gson.annotations.SerializedName

/**
 * This class exists only to match the JSON structure returned
 * by the API
 */
data class ForecastResponse(
    val properties: Properties
)
//main forecast data
data class Properties(
    //represents a forecast for a specific time
    val timeseries: List<TimeSeries>
)

/**
 * one forecast point in time
 * ISO-8601
 */
data class TimeSeries(
    //timestamp
    val time: String,
    //weather data with this timestamp
    val data: DataBlock
)

//contains different forecast blocks
data class DataBlock(
    //instant weather values
    val instant: InstantData,
    // forecast for the next 1 hour
    @SerializedName("next_1_hours")
    val next1Hours: NextHours?
)

data class InstantData(
    //detailed instant values
    val details: InstantDetails
)
//numeric values measured or predicted at this time
data class InstantDetails(
    // degrees Celsius by default
    @SerializedName("air_temperature")
    val airTemperature: Double?,
    // meters per second
    @SerializedName("wind_speed")
    val windSpeed: Double?,
    // percentage
    @SerializedName("relative_humidity")
    val relativeHumidity: Double?
)

// forecast information for the next 1 hour, nullable
data class NextHours(
    //summary information
    val summary: Summary?,
    //detailed forecast values
    val details: NextHoursDetails
)

/**
 * symbol describion the weather condition
 * "clearsky_day"
 * "lightrain"
 * "snow"
 * etc
 */
data class Summary(
    @SerializedName("symbol_code")
    val symbolCode: String?
)

// forecast values for the next hour
data class NextHoursDetails(
    // in millimeters
     @SerializedName("precipitation_amount")
    val precipitationAmount: Double?
)
