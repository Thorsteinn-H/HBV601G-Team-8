package `is`.hi.hbv601g.icelandicweatherapp.data

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "faerd", strict = false)
data class RoadResponse(

    @field:ElementList(name = "Faerd", inline = true)
    var roads: List<RoadConditionDto> = emptyList()
)