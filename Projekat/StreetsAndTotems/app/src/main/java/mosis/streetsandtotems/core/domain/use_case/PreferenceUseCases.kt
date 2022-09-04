package mosis.streetsandtotems.core.domain.use_case

data class PreferenceUseCases(
    val getUserSettings: GetUserSettings,
    val updateUserSettings: UpdateUserSettings,
    val getAuthProvider: GetAuthProvider,
    val updateAuthProvider: UpdateAuthProvider,
    val getUserSettingsFlow: GetUserSettingsFlow,
    val setSquadId: SetSquadId,
    val setUserId: SetUserId,
    val getUserId: GetUserId,
    val getSquadId: GetSquadId,
)
