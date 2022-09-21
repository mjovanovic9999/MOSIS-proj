package mosis.streetsandtotems.feature_auth.presentation.profile

import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.presentation.util.EditPasswordFields
import mosis.streetsandtotems.feature_auth.presentation.util.ProfileFields

data class ProfileScreenState(
    val formState: FormState<ProfileFields>,
    val passwordDialogFormState: FormState<EditPasswordFields>,
    val editMode: Boolean,
    val editPasswordDialogOpen: Boolean
)
