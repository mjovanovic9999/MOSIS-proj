package mosis.streetsandtotems.core.presentation.navigation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.presentation.navigation.util.BottomBarDestinations
import mosis.streetsandtotems.destinations.BackpackScreenDestination
import mosis.streetsandtotems.destinations.MapScreenDestination
import mosis.streetsandtotems.destinations.TotemsScreenDestination
import mosis.streetsandtotems.feature_backpack.presentation.BackpackViewModel
import mosis.streetsandtotems.feature_map.presentation.MapViewModel
import mosis.streetsandtotems.feature_totems.presentation.TotemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen(navController: NavHostController, drawerState: DrawerState) {
    Scaffold(bottomBar = {
        BottomBar(
            navController = navController,
            navGrahp = NavGraphs.main,
            destinations = BottomBarDestinations.DefaultDestinations()
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.main,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(MapScreenDestination) { drawerState }
                    dependency(MapScreenDestination) { hiltViewModel<MapViewModel>() }
                    dependency(BackpackScreenDestination) { hiltViewModel<BackpackViewModel>() }
                    dependency(TotemsScreenDestination) { hiltViewModel<TotemsViewModel>() }
                })
        }
    }
}

