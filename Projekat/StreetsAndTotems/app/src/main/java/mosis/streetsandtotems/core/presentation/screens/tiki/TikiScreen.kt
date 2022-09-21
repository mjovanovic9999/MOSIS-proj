package mosis.streetsandtotems.core.presentation.screens.tiki

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.screens.tiki.components.EmailVerificationContent
import mosis.streetsandtotems.core.presentation.screens.tiki.components.PasswordResetContent
import mosis.streetsandtotems.destinations.SignInScreenDestination
import mosis.streetsandtotems.ui.theme.sizes

enum class TikiScreenContentType {
    EmailVerification, PasswordReset
}

@RootNavGraph
@Destination
@Composable
fun TikiScreen(
    contentType: TikiScreenContentType,
    tikiScreenViewModel: TikiScreenViewModel,
    destinationsNavigator: DestinationsNavigator
) {
    val color = MaterialTheme.colorScheme.surfaceVariant
    val triangleHeightAspectRatio = MaterialTheme.sizes.tiki_message_triangle_aspect_ratio

    val state = tikiScreenViewModel.tikiScreenState.value
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        state.tikiScreenEventFlow.collect {
            when (it) {
                TikiScreenEvents.GoToSignInScreen -> {
                    destinationsNavigator.popBackStack(SignInScreenDestination, inclusive = false)
                }
                TikiScreenEvents.ClearFocus -> focusManager.clearFocus()
            }
        }
    }

    CustomPage(titleText = getTitle(contentType), content = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(MaterialTheme.sizes.tiki_message_aspect_ratio)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    drawPath(color = color, path = Path().apply {
                        lineTo(size.width, 0f)
                        lineTo(size.width / 2, size.width * triangleHeightAspectRatio)
                    })
                }
                Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
                    drawOval(
                        color = color
                    )
                })
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.9f),
                    text = getMessage(contentType),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                contentDescription = ImageContentDescriptionConstants.TOTEM,
            )
        }
        when (contentType) {
            TikiScreenContentType.EmailVerification -> {
                EmailVerificationContent(onResendEmailClick = {
                    tikiScreenViewModel.onEvent(
                        TikiScreenViewModelEvents.SendVerificationEmail
                    )
                }, onGoBackClick = {
                    tikiScreenViewModel.onEvent(TikiScreenViewModelEvents.GoToSignInScreen)
                })
            }
            TikiScreenContentType.PasswordReset -> {
                PasswordResetContent(formState = state.formState,
                    onSubmit = {
                        tikiScreenViewModel.onEvent(
                            TikiScreenViewModelEvents.SendRecoveryEmail
                        )
                    },
                    onGoBackClick = { tikiScreenViewModel.onEvent(TikiScreenViewModelEvents.GoToSignInScreen) })
            }
        }
    })
}

private fun getTitle(contentType: TikiScreenContentType): String {
    return when (contentType) {
        TikiScreenContentType.EmailVerification -> TitleConstants.EMAIL_VERIFICATION
        TikiScreenContentType.PasswordReset -> TitleConstants.PASSWORD_RESET
    }
}

private fun getMessage(contentType: TikiScreenContentType): String {
    return when (contentType) {
        TikiScreenContentType.EmailVerification -> MessageConstants.EMAIL_VERIFICATION
        TikiScreenContentType.PasswordReset -> MessageConstants.PASSWORD_RESET
    }
}

