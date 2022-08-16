package mosis.streetsandtotems.feature_totems.presentation

import mosis.streetsandtotems.feature_totems.domain.model.Totem

data class TotemsState(val totems: List<Totem>, val dialogOpen: Boolean, val selectedTotem: Int?)