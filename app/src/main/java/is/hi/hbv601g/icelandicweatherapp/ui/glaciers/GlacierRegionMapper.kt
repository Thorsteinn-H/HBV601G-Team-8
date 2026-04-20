package `is`.hi.hbv601g.icelandicweatherapp.ui.glaciers

data class GlacierLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Int
)

object GlacierRegionMapper {
    val glacierLocations = mapOf(
        "Snæfellsjökull" to GlacierLocation("Snæfellsjökull", 64.80, -23.78, 1446),
        "Langjökull" to GlacierLocation("Langjökull", 64.65, -20.30, 1360),
        "Hofsjökull" to GlacierLocation("Hofsjökull", 64.78, -18.92, 1765),
        "Drangajökull" to GlacierLocation("Drangajökull", 66.17, -22.22, 925),
        "Eyjafjallajökull" to GlacierLocation("Eyjafjallajökull", 63.63, -19.62, 1651),
        "Mýrdalsjökull" to GlacierLocation("Mýrdalsjökull", 63.63, -19.13, 1493),
        "Vatnajökull" to GlacierLocation("Vatnajökull", 64.42, -16.79, 2110)
    )
}