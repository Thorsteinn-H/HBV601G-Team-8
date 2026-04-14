package `is`.hi.hbv601g.icelandicweatherapp.model

import `is`.hi.hbv601g.icelandicweatherapp.data.FeatureDto

//extension function that converts featureDTO into RoadCondition
fun FeatureDto.toRoadCondition(): RoadCondition {
    return RoadCondition(
        // roadnumber if missing get unknow
        roadNumber = attributes.roadNumber ?: "Unknow",
        // road condition if missing
        condition = attributes.ast1Nafn ?: "Unknown",

        // convert geometry paths into list of coordinate pairs
        paths = geometry.paths?.map { path ->
            // each path is a list of points, convert each point to pair(x,y)
            path.map{ point ->
                Pair(point[0], point[1])
            }
        } ?: emptyList(),
        // color representing road condition
        colorHex = attributes.colorHex ?: "#FF0000"
    )
}