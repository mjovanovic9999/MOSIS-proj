package mosis.streetsandtotems.feature_auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.AuthNavGraph
import mosis.streetsandtotems.destinations.AuthScreenDestination
import mosis.streetsandtotems.destinations.MainScreenDestination

@AuthNavGraph(start = true)
@Destination
@Composable
fun AuthScreen(viewModel: AuthViewModel, destinationsNavigator: DestinationsNavigator) {
        CustomButton(clickHandler = {
            destinationsNavigator.navigate(MainScreenDestination) {
                popUpTo(AuthScreenDestination) { inclusive = true }
            }
        }, text = "TEST")
}

