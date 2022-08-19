package mosis.streetsandtotems.feature_map.presentation.util

import mosis.streetsandtotems.core.MapConstants.DEGREES_TO_RADIANS_COEFFICIENT
import mosis.streetsandtotems.core.MapConstants.LEVEL_COUNT
import mosis.streetsandtotems.core.MapConstants.TITLE_SIZE
import kotlin.math.PI
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.tan

fun calculateMapDimensions(): Int {
    return 2f.pow(LEVEL_COUNT - 1).toInt() * TITLE_SIZE
}

fun convertLatLngToOffsets(
    latitude: Double,
    longitude: Double,
    mapWidth: Int,
    mapHeight: Int
): DoubleArray {
    val FE = 180 // false easting
    val radius = mapWidth / (2 * PI)

    val latRad = degreesToRadians(latitude)
    val lonRad = degreesToRadians(longitude + FE)

    val x = lonRad * radius

    val yFromEquator = radius * ln(tan(PI / 4 + latRad / 2))
    val y = mapHeight / 2 - yFromEquator

    return doubleArrayOf(x / mapWidth, y / mapHeight)
}

fun degreesToRadians(degrees: Double): Double {
    return degrees * DEGREES_TO_RADIANS_COEFFICIENT
}