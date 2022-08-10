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
    val forgot_password_top_padding: Dp = (-20).dp,
    val icon: Dp = 24.dp,
    val add_photo_icon: Dp = 48.dp,
    val sign_up_form_height: Float = 0.54f,
    val form_default_spacing: Dp = 5.dp,
    val text_button_side_padding: Dp = 5.dp,
    val fab_image_heigth: Dp = 40.dp,
    val fab_padding: Dp = 10.dp,
    val image_select_size: Dp = 200.dp,
    val image_corner: Dp = 15.dp,
    val sign_up_button_top_padding: Dp = 5.dp,
    val drawer_column_width: Float = 0.9f,
    val drawer_switch_scale_factor: Float = 0.75f,
    val drawer_spacing: Dp = 10.dp,
    val drawer_image_size: Dp = 100.dp,
    val drawer_bullet_size: Dp = 12.dp,
    val drawer_icon_size: Dp = 36.dp,
    val drawer_spacer: Dp = 20.dp,
    val drawer_image_and_text_size: Dp = 120.dp,
    val drawer_leave_buttons_spacing: Dp = 20.dp,
    val drawer_select_height: Dp = 60.dp,
    val drawer_last_divider_top_padding: Dp = 8.dp,
)

val LocalWidth = compositionLocalOf { Sizes() }

val MaterialTheme.sizes: Sizes
    @Composable
    @ReadOnlyComposable
    get() = LocalWidth.current