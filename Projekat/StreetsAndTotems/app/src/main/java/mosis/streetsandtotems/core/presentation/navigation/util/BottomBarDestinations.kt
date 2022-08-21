package mosis.streetsandtotems.core.presentation.navigation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.NavBarConstants
import mosis.streetsandtotems.destinations.BackpackScreenDestination
import mosis.streetsandtotems.destinations.MapScreenDestination
import mosis.streetsandtotems.destinations.TotemsScreenDestination


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