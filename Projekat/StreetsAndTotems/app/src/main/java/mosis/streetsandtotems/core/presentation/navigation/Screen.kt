package mosis.streetsandtotems.core.presentation.navigation

import mosis.streetsandtotems.core.NavigationConstants

sealed class Screen(val route: String){
    object AuthScreen: Screen(NavigationConstants.AUTH_SCREEN)
}
