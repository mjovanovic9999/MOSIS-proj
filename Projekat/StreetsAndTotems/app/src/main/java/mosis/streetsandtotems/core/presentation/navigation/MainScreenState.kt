package mosis.streetsandtotems.core.presentation.navigation

import kotlinx.coroutines.flow.StateFlow

data class MainScreenState(val mainScreenEventFlow: StateFlow<MainScreenEvents?>)