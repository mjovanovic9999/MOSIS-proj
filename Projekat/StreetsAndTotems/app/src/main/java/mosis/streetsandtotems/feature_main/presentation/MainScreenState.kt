package mosis.streetsandtotems.feature_main.presentation

import android.net.Uri
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.SharedFlow
import mosis.streetsandtotems.core.domain.model.UserSettings

@OptIn(ExperimentalMaterial3Api::class)
data class MainScreenState(
    val mainScreenEventFlow: SharedFlow<MainScreenEvents>,
    val locationEnabled: Boolean,
    val userSettings: UserSettings,
    val drawerState: DrawerState,
    val firstName: String,
    val lastName: String,
    val username: String,
    val imageUri: Uri
)