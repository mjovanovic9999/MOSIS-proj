package mosis.streetsandtotems.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizes(
    val default_form_width: Float = 0.75f,
    val text_button_min_height: Dp = 12.dp,
    val none: Dp = 0.dp,
    val forgot_password_top_padding: Dp = (-20).dp,
    val icon: Dp = 24.dp,
    val add_photo_icon: Dp = 48.dp,
    val sign_up_form_height: Float = 0.54f,
    val form_default_spacing: Dp = 5.dp,
    val text_button_side_padding: Dp = 5.dp,
    val fab_padding: Dp = 10.dp,
    val image_select_size: Dp = 200.dp,
    val default_shape_corner: Dp = 15.dp,
    val sign_up_button_top_padding: Dp = 5.dp,
    val drawer_column_padding: Dp = 15.dp,
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
    val profile_screen_spacing: Dp = 20.dp,
    val title_icon: Dp = 36.dp,
    val title_spacing: Dp = 5.dp,
    val text_logo_top_padding: Dp = 10.dp,
    val default_aspect_ratio: Float = 1f,
    val backpack_totem_fab_weight: Float = 0.65f,
    val backpack_item_fab_weight: Float = 0.4f,
    val backpack_spacer_weight: Float = 0.2f,
    val backpack_item_badge_size: Float = 0.35f,
    val backpack_badge_padding: Dp = 5.dp,
    val backpack_tiki_badge_size: Float = 0.25f,
    val backpack_item_icon_size: Float = 0.5f,
    val drop_item_dialog_icon_size: Dp = 48.dp,
    val drop_item_dialog_icon_padding: Dp = 10.dp,
    val drop_item_dialog_width: Float = 0.9f,
    val drop_item_dialog_amount_text_field_height: Dp = 60.dp,
    val drop_item_dialog_spacer: Dp = 10.dp,
    val lazy_column_spacing: Dp = 10.dp,
    val lazy_column_width: Float = 0.95f,
    val totems_item_totem_id_weight: Float = 0.3f,
    val totems_item_username_weight: Float = 0.6f,
    val lazy_column_button_weight: Float = 0.1f,
    val lazy_column_max_width: Float = 1f,
    val totems_tiki_height: Float = 0.3f
)

val LocalWidth = compositionLocalOf { Sizes() }

val MaterialTheme.sizes: Sizes
    @Composable
    @ReadOnlyComposable
    get() = LocalWidth.current