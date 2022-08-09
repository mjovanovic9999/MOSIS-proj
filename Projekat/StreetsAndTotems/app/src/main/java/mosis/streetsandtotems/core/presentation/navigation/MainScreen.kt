package mosis.streetsandtotems.core.presentation.navigation

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.presentation.components.CustomRequestLocation
import mosis.streetsandtotems.destinations.MapScreenDestination
import mosis.streetsandtotems.feature_map.presentation.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() },
        content = { DrawerScreen(navController = navController, drawerState = drawerState) }
    )

    CustomRequestLocation(LocalContext.current)
}

@Composable

private fun DrawerContent() {
    Row() {

    }
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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            DestinationsNavHost(
                navGraph = NavGraphs.main,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(MapScreenDestination) { drawerState }
                    dependency(MapScreenDestination) { hiltViewModel<MapViewModel>() }
                })
        }
    }
}


