package mosis.streetsandtotems.services.use_case

data class LocationServiceUseCases(
    val updatePlayerLocation: UpdatePlayerLocation,
    val registerCallbacks: RegisterCallbacks,
    val removeCallbacks: RemoveCallbacks,
    val changeUserOnlineStatus: ChangeUserOnlineStatus,
    val registerCallbackOnSquadInvite: RegisterCallbackOnSquadInvite,
    val registerCallbackOnKickVote: RegisterCallbackOnKickVote,
    val removeCallbackOnKickVote: RemoveCallbackOnKickVote,
    val removeCallbackOnSquadInvite: RemoveCallbackOnSquadInvite
)
