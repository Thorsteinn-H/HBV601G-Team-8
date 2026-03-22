package `is`.hi.hbv601g.icelandicweatherapp.model

import `is`.hi.hbv601g.icelandicweatherapp.data.FeatureDto

//extension function that converts featureDTO into RoadCondition
fun FeatureDto.toRoadCondition(): RoadCondition {
    return RoadCondition(
        roadNumber = attributes.roadNumber ?: "Unknow",
        condition = attributes.ast1Nafn ?: "Unknown",
        paths = geometry.paths?.map { path ->
            path.map{ point ->
                Pair(point[0], point[1])
            }
        } ?: emptyList(),
        colorHex = attributes.colorHex ?: "#FF0000"
    )
}