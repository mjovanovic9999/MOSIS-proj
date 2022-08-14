package mosis.streetsandtotems.core.presentation.navigation

import android.Manifest
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import androidx.work.*
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.DrawerConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.destinations.MapScreenDestination
import mosis.streetsandtotems.feature_map.presentation.MapViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerContent
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerScreen
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestPermission


//import mosis.streetsandtotems.services.LocationWorker

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen(destinationsNavigator: DestinationsNavigator) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    val context = LocalContext.current

    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                Modifier.align(Alignment.CenterHorizontally),
                destinationsNavigator
            )
        },
        content = { DrawerScreen(navController = navController, drawerState = drawerState) }
    )
}






