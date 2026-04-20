package `is`.hi.hbv601g.icelandicweatherapp.model

//domain model used inside the app
data class RoadCondition(
    val roadNumber: String, //road number
    val condition: String, //condition of the road
    val paths: List<List<Pair<Double, Double>>>, // road geometry
    val colorHex: String? // color from the api
)
