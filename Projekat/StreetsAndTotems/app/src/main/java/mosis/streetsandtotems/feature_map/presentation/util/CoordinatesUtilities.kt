package mosis.streetsandtotems.feature_map.presentation.util

import mosis.streetsandtotems.core.MapConstants.degreesToRadiansCoefficient
import mosis.streetsandtotems.core.MapConstants.levelCount
import mosis.streetsandtotems.core.MapConstants.tileSize
import kotlin.math.PI
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.tan

fun calculateMapDimensions(): Int {
    return 2f.pow(levelCount - 1).toInt() * tileSize
}

fun latLonToOffsets(
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
    return degrees * degreesToRadiansCoefficient
}