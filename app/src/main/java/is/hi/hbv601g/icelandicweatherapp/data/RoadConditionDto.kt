package `is`.hi.hbv601g.icelandicweatherapp.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Faerd", strict = false)
data class RoadConditionDto(

    @field:Element(name = "Vegheiti", required = false)
    var roadName: String? = null,

    @field:Element(name = "AstandLysingEn", required = false)
    var description: String? = null,

    @field:Element(name = "AstandYfirbord", required = false)
    var surface: String? = null,

    @field:Element(name = "Nordur", required = false)
    var lat: Double? = null,

    @field:Element(name = "Austur", required = false)
    var lon: Double? = null
)
