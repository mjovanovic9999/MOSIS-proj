package mosis.streetsandtotems.core.presentation.screens.tiki.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType

@Composable
fun EmailVerificationContent(onResendEmailClick: () -> Unit, onGoBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CustomButton(
            clickHandler = onResendEmailClick,
            buttonType = CustomButtonType.Outlined,
            text = ButtonConstants.RESEND_EMAIL,
            matchParentWidth = true,
            textStyle = MaterialTheme.typography.labelLarge
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