package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mosis.streetsandtotems.feature_auth.presentation.components.AuthScreen


@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController= navController, startDestination = Screen.AuthScreen.route){
        composable(route = Screen.AuthScreen.route){
            AuthScreen()
        }


    }
}