package mosis.streetsandtotems.feature_main.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import mosis.streetsandtotems.destinations.DirectionDestination

data class BottomBarDestination(
    val direction: DirectionDestination,
    val icon: @Composable () -> ImageVector,
    val label: String
)
