package mosis.streetsandtotems.core.presentation.navigation

//import androidx.work.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerContent
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerScreen
import mosis.streetsandtotems.core.presentation.utils.notification.BackgroundServicesEnabled
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
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


    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    LifecycleCompose(
        viewModel.locationBroadcastReceiver,
        viewModel.notificationProvider
    )

    CustomRequestLocation(LocationService.isLocationEnabled)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize()
                        .padding(MaterialTheme.sizes.drawer_column_padding),
                    destinationsNavigator
                )
            }
        },
        content = { DrawerScreen(navController = navController, drawerState = drawerState) }
    )
}


@Composable
fun LifecycleCompose(
    locationBroadcastReceiver: LocationBroadcastReceiver,
    notificationProvider: NotificationProvider
) {


    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {

                        context.registerReceiver(
                            locationBroadcastReceiver,
                            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
                        )
                        if (!LocationService.isServiceStarted) {
                            Log.d("tag", "startuje servis")

                            context.startForegroundService(
                                Intent(
                                    context,
                                    LocationService::class.java
                                )
                            )

                        } else {
                            if (BackgroundServicesEnabled.isEnabled) {
                                notificationProvider.notifyDisable(false)
                            }


                        }

                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        if (BackgroundServicesEnabled.isEnabled && LocationService.isServiceStarted) {
                            notificationProvider.notifyDisable(true)
                        } else {
                            context.stopService(Intent(context, LocationService::class.java))
                        }

                        context.unregisterReceiver(
                            locationBroadcastReceiver,
                        )
                    }
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}

