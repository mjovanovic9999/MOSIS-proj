package mosis.streetsandtotems.feature_totems.domain.model

import java.time.LocalDateTime
import java.util.*

data class Totem(
    val id: String,
    val placedBy: String,
    val placingTime: LocalDateTime,
    val lastVisited: LocalDateTime
)
