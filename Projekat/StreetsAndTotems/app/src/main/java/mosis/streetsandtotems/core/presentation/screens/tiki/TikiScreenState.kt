package mosis.streetsandtotems.core.presentation.screens.tiki

import kotlinx.coroutines.flow.SharedFlow
import mosis.streetsandtotems.core.domain.util.PasswordResetFields
import mosis.streetsandtotems.core.presentation.states.FormState

data class TikiScreenState(
    val tikiScreenEventFlow: SharedFlow<TikiScreenEvents>,
    val formState: FormState<PasswordResetFields>
)
