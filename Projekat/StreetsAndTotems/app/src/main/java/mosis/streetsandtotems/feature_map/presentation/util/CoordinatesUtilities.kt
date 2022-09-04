package mosis.streetsandtotems.feature_map.presentation.util

import android.location.Location
import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.core.MapConstants.DEGREES_TO_RADIANS_COEFFICIENT
import mosis.streetsandtotems.core.MapConstants.LEVEL_COUNT
import mosis.streetsandtotems.core.MapConstants.MAP_PRECISION_METERS
import mosis.streetsandtotems.core.MapConstants.MAXIMUM_TRADE_DISTANCE_IN_METERS
import mosis.streetsandtotems.core.MapConstants.RADIANS_TO_DEGREES_COEFFICIENT
import mosis.streetsandtotems.core.MapConstants.TITLE_SIZE
import kotlin.math.*

fun calculateMapDimensions(): Int {
    return 2f.pow(LEVEL_COUNT - 1).toInt() * TITLE_SIZE
}

fun convertLatLngToOffsets(
    latitude: Double,
    longitude: Double,
    mapWidth: Int,
    mapHeight: Int,
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

fun convertGeoPointToOffsets(
    geoPoint: GeoPoint,
    mapWidth: Int,
    mapHeight: Int,
): DoubleArray {
    val FE = 180 // false easting
    val radius = mapWidth / (2 * PI)

    val latRad = degreesToRadians(geoPoint.latitude)
    val lonRad = degreesToRadians(geoPoint.longitude + FE)

    val x = lonRad * radius

    val yFromEquator = radius * ln(tan(PI / 4 + latRad / 2))
    val y = mapHeight / 2 - yFromEquator

    return doubleArrayOf(x / mapWidth, y / mapHeight)
}

fun convertGeoPointNullToOffsets(
    geoPoint: GeoPoint?,
    mapWidth: Int,
    mapHeight: Int,
): DoubleArray {
    if (geoPoint != null)
        return convertGeoPointToOffsets(geoPoint, mapWidth, mapHeight)
    return doubleArrayOf(0.0, 0.0)
}

fun convertOffsetsToGeoPoint(
    x: Double,
    y: Double,
    mapWidth: Int,
    mapHeight: Int,
): GeoPoint {
    val FE = 180 // false easting
    val radius = mapWidth / (2 * PI)

    val xTemp = x * mapWidth

    val lonRad = xTemp / radius

    val longitude = radiansToDegrees(lonRad) - FE


    val yTemp = y * mapHeight

    val yFromEquator = mapHeight / 2 - yTemp

    val latRad = 2 * (atan(exp(yFromEquator / radius)) - PI / 4)

    val latitude = radiansToDegrees(latRad)


    return GeoPoint(latitude, longitude)
}

fun degreesToRadians(degrees: Double): Double = degrees * DEGREES_TO_RADIANS_COEFFICIENT

fun radiansToDegrees(radians: Double): Double = radians * RADIANS_TO_DEGREES_COEFFICIENT

fun areGeoPointsEqual(x1: GeoPoint, x2: GeoPoint): Boolean =
    distanceBetweenGeoPoints(x1, x2) <= MAP_PRECISION_METERS

fun distanceBetweenGeoPoints(
    x1: GeoPoint, x2: GeoPoint
): Float {
    val result = floatArrayOf(0f)
    Location.distanceBetween(x1.latitude, x1.longitude, x2.latitude, x2.longitude, result)
    return result[0]
}

fun isTradePossible(myLocation: GeoPoint?, otherPlayerLocation: GeoPoint?): Boolean =
    if (myLocation != null && otherPlayerLocation != null)
        distanceBetweenGeoPoints(
            GeoPoint(myLocation.latitude, myLocation.longitude), otherPlayerLocation
        ) <= MAXIMUM_TRADE_DISTANCE_IN_METERS
    else false