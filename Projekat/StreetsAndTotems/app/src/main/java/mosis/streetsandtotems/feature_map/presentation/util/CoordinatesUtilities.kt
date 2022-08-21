package mosis.streetsandtotems.feature_map.presentation.util

import mosis.streetsandtotems.core.MapConstants.COMPARISON_PRECISION
import mosis.streetsandtotems.core.MapConstants.DEGREES_TO_RADIANS_COEFFICIENT
import mosis.streetsandtotems.core.MapConstants.LEVEL_COUNT
import mosis.streetsandtotems.core.MapConstants.TITLE_SIZE
import ovh.plrapps.mapcompose.api.centroidX
import kotlin.math.*

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

fun areOffsetsEqual(x1: Double, x2: Double): Boolean =
    (x1 * COMPARISON_PRECISION).roundToInt() == (x2 * COMPARISON_PRECISION).roundToInt()
