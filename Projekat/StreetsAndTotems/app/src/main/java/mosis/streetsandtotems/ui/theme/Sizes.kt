package mosis.streetsandtotems.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizes (
    val auth_screen_form_width: Float = 0.75f,
    val text_button_min_heigth: Dp = 12.dp,
    val none: Dp = 0.dp,
    val forgot_password_top_padding: Dp = (-35).dp,
    val icon: Dp = 24.dp,
)

val LocalWidth = compositionLocalOf { Sizes() }

val MaterialTheme.sizes: Sizes
    @Composable
    @ReadOnlyComposable
    get() = LocalWidth.current