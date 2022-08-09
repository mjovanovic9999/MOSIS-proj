package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import mosis.streetsandtotems.destinations.DirectionDestination
import androidx.compose.material.icons.outlined.Map
import androidx.compose.ui.res.vectorResource
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.NavBarConstants
import mosis.streetsandtotems.destinations.BackpackScreenDestination
import mosis.streetsandtotems.destinations.MapScreenDestination
import mosis.streetsandtotems.destinations.TotemsScreenDestination

data class BottomBarDestination(
    val direction: DirectionDestination,
    val icon: @Composable () -> ImageVector,
    val label: String
)

sealed class BottomBarDestinations(open val destinations: List<BottomBarDestination>) {
    data class DefaultDestinations(
        override val destinations: List<BottomBarDestination> = listOf(
            BottomBarDestination(
                BackpackScreenDestination, {
                    ImageVector.vectorResource(
                        id = R.drawable.backpack
                    )
                },
                NavBarConstants.BACKPACK
            ),
            BottomBarDestination(
                MapScreenDestination, {
                    Icons.Outlined.Map
                },
                NavBarConstants.MAP
            ),
            BottomBarDestination(
                TotemsScreenDestination, {
                    ImageVector.vectorResource(
                        id = R.drawable.totem
                    )
                },
                NavBarConstants.TOTEMS
            ),
        )
    ) : BottomBarDestinations(destinations)
}