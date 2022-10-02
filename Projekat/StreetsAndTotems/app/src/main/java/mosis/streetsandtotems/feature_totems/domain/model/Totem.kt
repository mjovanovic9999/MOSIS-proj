package mosis.streetsandtotems.feature_totems.domain.model

import java.time.LocalDateTime

data class Totem(
    val id: String,
    val placedBy: String,
    val placingTime: LocalDateTime,
    val lastVisited: LocalDateTime
)
