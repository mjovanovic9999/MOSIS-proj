package mosis.streetsandtotems.services

sealed class LocationServiceControlEvents {
    object RegisterCallbacks : LocationServiceControlEvents()
    object RemoveCallbacks : LocationServiceControlEvents()
}
