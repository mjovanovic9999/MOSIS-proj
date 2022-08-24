package mosis.streetsandtotems.feature_main.presentation

//import androidx.work.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import mosis.streetsandtotems.feature_main.presentation.components.DrawerContent
import mosis.streetsandtotems.feature_main.presentation.components.DrawerScreen
import mosis.streetsandtotems.feature_main.presentation.components.LifecycleCompose
import mosis.streetsandtotems.destinations.SignInScreenDestination
import mosis.streetsandtotems.feature_main.presentation.MainScreenEvents
import mosis.streetsandtotems.feature_main.presentation.MainScreenViewModel
import mosis.streetsandtotems.feature_main.presentation.MainScreenViewModelEvents
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestLocation
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.ui.theme.sizes


@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: MainScreenViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val state = viewModel.mainScreenState.value

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        state.mainScreenEventFlow.collectLatest {
            if (it != null)
                when (it) {
                    MainScreenEvents.SignOutSuccessful -> {
                        destinationsNavigator.popBackStack()
                        destinationsNavigator.navigate(SignInScreenDestination)
                    }
                }
        }
    }

    LifecycleCompose(
        viewModel.locationBroadcastReceiver,
        viewModel.notificationProvider
    )

    //CustomRequestLocation(LocationService.isLocationEnabled)

//    MapScreen(drawerState = drawerState, mapViewModel = hiltViewModel())
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize()
                        .padding(MaterialTheme.sizes.drawer_column_padding),
                    destinationsNavigator,
                    onSignOut = { viewModel.onEvent(MainScreenViewModelEvents.SignOut) }
                )
            }
        },
        content = { DrawerScreen(navController = navController, drawerState = drawerState) }
    )
}



