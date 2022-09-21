package mosis.streetsandtotems.feature_main.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.SharedFlow
import mosis.streetsandtotems.core.domain.model.UserSettings
import mosis.streetsandtotems.feature_map.domain.model.ProfileData

@OptIn(ExperimentalMaterial3Api::class)
data class MainScreenState(
    val mainScreenEventFlow: SharedFlow<MainScreenEvents>,
    val locationEnabled: Boolean,
    val userSettings: UserSettings,
    val drawerState: DrawerState,
    val currentUserData: ProfileData
)