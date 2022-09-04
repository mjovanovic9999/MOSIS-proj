package mosis.streetsandtotems.feature_main.presentation

import kotlinx.coroutines.flow.StateFlow

data class MainScreenState(
    val mainScreenEventFlow: StateFlow<MainScreenEvents?>,
    val locationEnabled: Boolean
)