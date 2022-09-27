package mosis.streetsandtotems.feature_totems.domain.use_case

data class TotemUseCases(
    val registerTotemCallbacks: RegisterTotemCallbacks,
    val removeTotemCallbacks: RemoveTotemCallbacks,
    val getUsername: GetUsername
)