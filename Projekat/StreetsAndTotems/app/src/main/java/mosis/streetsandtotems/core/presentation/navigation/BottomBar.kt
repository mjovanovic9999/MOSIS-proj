package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import mosis.streetsandtotems.NavGraph
import mosis.streetsandtotems.appCurrentDestinationAsState
import mosis.streetsandtotems.destinations.Destination
import mosis.streetsandtotems.destinations.MainScreenDestination
import mosis.streetsandtotems.startAppDestination

@Composable
fun BottomBar(
    navController: NavHostController,
    navGrahp: NavGraph,
    destinations: BottomBarDestinations
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: navGrahp.startAppDestination

    NavigationBar {
        destinations.destinations.forEach { destination ->
            val isCurrentDestOnBackStack = navController.isRouteOnBackStack(destination.direction)
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    if (isCurrentDestOnBackStack) {
                        navController.popBackStack(destination.direction, false)
                        return@NavigationBarItem
                    }

                    navController.navigate(destination.direction) {
                        popUpTo(MainScreenDestination) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },
                icon = { Icon(destination.icon(), contentDescription = destination.label) },
                label = { Text(destination.label) },
            )
        }
    }
}
