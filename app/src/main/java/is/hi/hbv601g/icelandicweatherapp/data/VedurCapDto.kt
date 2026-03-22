package `is`.hi.hbv601g.icelandicweatherapp.data

data class VedurCapRegionDto(
    val id: Int,
    val name: String? = null
)

data class VedurCapRegionDetailDto(
    val id: Int,
    val name: String? = null,
    val polygons: List<Any>? = null
)