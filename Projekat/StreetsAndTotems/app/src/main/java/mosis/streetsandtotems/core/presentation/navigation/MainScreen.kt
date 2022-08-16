package mosis.streetsandtotems.core.presentation.navigation

//import androidx.work.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerContent
import mosis.streetsandtotems.core.presentation.navigation.components.DrawerScreen
import mosis.streetsandtotems.ui.theme.sizes


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






