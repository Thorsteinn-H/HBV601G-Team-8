package `is`.hi.hbv601g.icelandicweatherapp.data

import com.google.gson.annotations.SerializedName

// Root response from ArcGIS API
data class RoadResponseDto(
    val features: List<FeatureDto>
)

//each feature represents one road segment
data class FeatureDto(
    //data about the road
    val attributes: AttributesDto,
    // coordinates for drawing the road
    val geometry: GeometryDto,
)

//all usefull descriptive data
data class AttributesDto(
    //Road condition
    @SerializedName("AST1_NAFN")
    val ast1Nafn: String?,

    // Road number
    @SerializedName("NRVEGUR")
    val roadNumber: String?,

    // internal road id
    val NR_VEGL: Int?,

    //last update
    val TIMIKEYRSLA: Long?,

    //road name
    @SerializedName("NAFN_LEIDAR")
    val roadName: String?,

    //color provided by the API
    @SerializedName("AST1_LITUR")
    val colorHex: String?
)

//geometry of the road(polyline)
data class GeometryDto(
    val paths: List<List<List<Double>>>
)