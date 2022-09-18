package mosis.streetsandtotems.core.presentation.screens.tiki.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.domain.util.PasswordResetFields
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.core.presentation.states.FormState

@Composable
fun PasswordResetContent(
    formState: FormState<PasswordResetFields>,
    onSubmit: () -> Unit,
    onGoBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Form(formState = formState)
        CustomButton(
            clickHandler = onSubmit,
            text = ButtonConstants.RECOVERY_EMAIL,
            buttonType = CustomButtonType.Outlined,
            matchParentWidth = true,
            textStyle = MaterialTheme.typography.labelLarge,
            enabled = formState.isFormFilled.value
        )
        CustomButton(
            clickHandler = onGoBackClick,
            buttonType = CustomButtonType.Outlined,
            text = ButtonConstants.GO_TO_SIGN_IN,
            matchParentWidth = true,
            textStyle = MaterialTheme.typography.labelLarge
        )
    }

}
