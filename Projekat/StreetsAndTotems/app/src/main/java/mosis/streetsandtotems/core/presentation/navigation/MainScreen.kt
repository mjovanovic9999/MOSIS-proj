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
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerContent
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerScreen
import mosis.streetsandtotems.feature_map.domain.LocationDTO
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestPermission


@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen(destinationsNavigator: DestinationsNavigator) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    var myLocation = remember { mutableStateOf(LocationDTO(-1.0, -1.0, -1.0f)) }



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
    CustomRequestPermission(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
}






