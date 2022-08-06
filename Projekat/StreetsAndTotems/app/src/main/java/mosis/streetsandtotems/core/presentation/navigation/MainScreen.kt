package mosis.streetsandtotems.core.presentation.navigation

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.spec.Route
import kotlinx.coroutines.launch
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.presentation.components.CustomRequestPermissions
import mosis.streetsandtotems.destinations.MainScreenDestination
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
}

@Composable
private fun DrawerContent() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerScreen(navController: NavHostController, drawerState: DrawerState) {
    CustomRequestPermissions(LocalContext.current)
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


