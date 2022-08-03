package mosis.streetsandtotems.feature_map.domain

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}