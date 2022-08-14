package mosis.streetsandtotems.feature_totems.domain.model

import java.util.*

data class Totem(
    val id: String,
    val placedBy: String,
    val placingTime: Date,
    val lastVisited: Date
)