package `is`.hi.hbv601g.icelandicweatherapp.model

object IcelandLocations {
    /**
     * hard coded coordinets for weather lookups
     */
val majorIcelandLocation: List<Location> = listOf(
    //capital region
        Location("Reykjavík", 64.1466, -21.9426, "Capital Region"),
        Location("Kópavogur", 64.1123, -21.9120, "Capital Region"),
        Location("Hafnarfjörður", 64.0671, -21.9377, "Capital Region"),
        Location("Garðabær", 64.0886, -21.9226, "Capital Region"),
        Location("Mosfellsbær", 64.1667, -21.7000, "Capital Region"),
    //north
        Location("Akureyri", 65.6885, -18.1262, "North"),
        Location("Húsavík", 66.0449, -17.3389, "North"),
    //east
        Location("Egilsstaðir", 65.2669, -14.3948, "East"),
        Location("Neskaupstaður", 65.1482, -13.6837, "East"),
        Location("Seyðisfjörður", 65.2593, -14.0094, "East"),
    //south
        Location("Selfoss", 63.9334, -21.0016, "South"),
        Location("Hveragerði", 64.0004, -21.1866, "South"),
        Location("Vík", 63.4189, -18.9980, "South"),
        Location("Höfn", 64.2539, -15.2121, "South"),
    //west
        Location("Akranes", 64.3218, -22.0740, "West"),
        Location("Borgarnes", 64.5383, -21.9206, "West"),
    //westfjords
        Location("Ísafjörður", 66.0759, -23.1350, "Westfjords"),
    //Reykjanes
        Location("Keflavík", 63.9998, -22.5583, "Reykjanes"),
        Location("Grindavík", 63.8385, -22.4383, "Reykjanes"),
    // South interior
        Location("Kirkjubæjarklaustur", 63.7902, -18.0644, "South")
)



}