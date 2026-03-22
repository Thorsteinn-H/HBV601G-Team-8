package `is`.hi.hbv601g.icelandicweatherapp.utilities

import org.locationtech.proj4j.*

//converts coordinates from Icelandic prjection to standard GPS coordinates
fun convertToLatLng(x: Double, y: Double): Pair<Double, Double> {
    // used to define coordinate reference systems
    val crsFactory = CRSFactory()

    //Icelands local coordinate system
    val sourceCRS = crsFactory.createFromParameters(
        "ISN93",
        "+proj=lcc +lat_1=64.25 +lat_2=65.75 +lat_0=65 +lon_0=-19 +x_0=500000 +y_0=500000 +ellps=GRS80 +units=m +no_defs"
    )

    //standard gps coordinates
    val targetCRS = crsFactory.createFromParameters(
        "WGS84",
        "+proj=longlat +datum=WGS84 +no_defs"
    )

    //object to convert between the two systems
    val transform = CoordinateTransformFactory()
        .createTransform(sourceCRS, targetCRS)

    // source coordinate
    val src = ProjCoordinate(x, y)
    // destinatino coordinate
    val dst = ProjCoordinate()

    //transformation
    transform.transform(src,dst)

    //returned as latitude and longitude
    return Pair(dst.y, dst.x)

}