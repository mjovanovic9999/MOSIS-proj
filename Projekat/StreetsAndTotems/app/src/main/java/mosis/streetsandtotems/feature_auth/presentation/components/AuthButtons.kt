package mosis.streetsandtotems.feature_auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.ui.theme.facebook_sign_in
import mosis.streetsandtotems.ui.theme.google_sign_in
import mosis.streetsandtotems.ui.theme.sizes


enum class AuthButtonsType {
    SignIn,
    SignUp
}

@Composable
fun AuthButtons(type: AuthButtonsType) {
    Column() {
                CustomButton(
                    matchParentWidth = true,
                    clickHandler = { /*TODO*/ },
                    buttonType = CustomButtonType.Filled,
                    text = if(type == AuthButtonsType.SignUp) ButtonConstants.SIGN_UP_GOOGLE else ButtonConstants.SIGN_IN_GOOGLE,
                    icon = ImageVector.vectorResource(
                        id = R.drawable.google
                    ),
                    iconSize = MaterialTheme.sizes.icon,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = google_sign_in
                    ),
                    textStyle = MaterialTheme.typography.titleMedium
                )
                CustomButton(
                    matchParentWidth = true,
                    clickHandler = { /*TODO*/ },
                    buttonType = CustomButtonType.Filled,
                    text = if(type == AuthButtonsType.SignUp) ButtonConstants.SIGN_UP_FACEBOOK else ButtonConstants.SIGN_IN_FACEBOOK,
                    icon = ImageVector.vectorResource(id = R.drawable.facebook),
                    iconSize = MaterialTheme.sizes.icon,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = facebook_sign_in
                    ),
                    textStyle = MaterialTheme.typography.titleMedium
                )
    }
}