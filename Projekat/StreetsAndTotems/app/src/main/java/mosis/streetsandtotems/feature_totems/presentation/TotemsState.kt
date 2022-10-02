package mosis.streetsandtotems.feature_totems.presentation

import mosis.streetsandtotems.feature_map.domain.model.TotemData

data class TotemsState(
    val totems: List<TotemData>, val dialogOpen: Boolean, val selectedTotem: Int?
)