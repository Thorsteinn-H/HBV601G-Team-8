package `is`.hi.hbv601g.icelandicweatherapp.data

data class VolcanoDto (
    //name of volcano in english
    val volcano_name: String,
    // in icelandic
    val volcano_name_si: String,
    //unique id for volcano
    val volcano_id: Int,
    //cooridantes
    val latitude: Double,
    val longitude: Double,
    //activity status
    val status: String,
    //when status was puplished
    val publication_date: String,
    //when it should end
    val end_date: String,
    //source
    val source_of_information: String
)

