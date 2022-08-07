package mosis.streetsandtotems.core.presentation.navigation

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.presentation.components.CustomRequestPermissions
import mosis.streetsandtotems.destinations.MapScreenDestination
import mosis.streetsandtotems.feature_map.presentation.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen(locationPermissionRequest: ActivityResultLauncher<Array<String>>) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    locationPermissionRequest.launch(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() },
        content = { DrawerScreen(navController = navController, drawerState = drawerState) }
    )

    CustomRequestPermissions(LocalContext.current, locationPermissionRequest)
}

@Composable
private fun DrawerContent() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerScreen(navController: NavHostController, drawerState: DrawerState) {
    Scaffold(bottomBar = {
        BottomBar(
            navController = navController,
            navGrahp = NavGraphs.main,
            BottomBarDestinations.DefaultDestinations()
        )
    }) {
        DestinationsNavHost(
            navGraph = NavGraphs.main,
            navController = navController,
            dependenciesContainerBuilder = {
                dependency(MapScreenDestination) { drawerState }
                dependency(MapScreenDestination) { hiltViewModel<MapViewModel>() }
            })
    }
}


