package `is`.hi.hbv601g.icelandicweatherapp.data

data class VolcanoDto (
    val volcano_name: String,
    val volcano_name_si: String,
    val volcano_id: Int,
    val latitude: Double,
    val longitude: Double,
    val status: String,
    val publication_date: String,
    val end_date: String,
    val source_of_information: String
)

