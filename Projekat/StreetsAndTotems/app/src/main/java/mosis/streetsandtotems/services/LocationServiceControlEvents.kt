package mosis.streetsandtotems.services

sealed class LocationServiceControlEvents {
    object RegisterCallbacks : LocationServiceControlEvents()
    object RemoveCallbacks : LocationServiceControlEvents()
    object RegisterSquadInviteCallback : LocationServiceControlEvents()
    object RemoveSquadInviteCallback : LocationServiceControlEvents()
    object RegisterKickVoteCallback : LocationServiceControlEvents()
    object RemoveKickVoteCallback : LocationServiceControlEvents()
}
