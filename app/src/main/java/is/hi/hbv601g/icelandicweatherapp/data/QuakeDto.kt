package `is`.hi.hbv601g.icelandicweatherapp.data

data class QuakeDto (
    val type: String,
    //list of individual earthquake events
    val features: List<Feature>)

// A single earthquake entry
data class Feature(
    val type: String,
    //location of the earthquake
    val geometry: Geometry,
    //detailed earthquake data
    val properties: Properties,)

data class Geometry(
    val type: String,
    //coordinates, longitude, latitude
    val coordinates: List<Double>)

data class Properties(
    val event_id:String,
    val time: String,
    // strenght of the earthquake
    val magnitude: Double,
    //depth in km
    val depth: Double,
    val status: String,
    //location name
    val region: String,
    val type: String,
    val evaluation_mode: String,
    //last update time
    val updated_time: String,
    )
