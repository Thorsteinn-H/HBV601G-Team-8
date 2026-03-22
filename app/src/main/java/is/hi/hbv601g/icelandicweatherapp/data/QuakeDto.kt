package `is`.hi.hbv601g.icelandicweatherapp.data

data class QuakeDto (
    val type: String,
    val features: List<Feature>)

data class Feature(
    val type: String,
    val geometry: Geometry,
    val properties: Properties,)

data class Geometry(
    val type: String,
    val coordinates: List<Double>)

data class Properties(
    val event_id:String,
    val time: String,
    val magnitude: Double,
    val depth: Double,
    val status: String,
    val region: String,
    val type: String,
    val evaluation_mode: String,
    val updated_time: String,
    )
