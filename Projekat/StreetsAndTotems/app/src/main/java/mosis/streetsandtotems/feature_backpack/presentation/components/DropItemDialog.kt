package mosis.streetsandtotems.feature_backpack.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.ItemsConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomTextField
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.feature_backpack.presentation.DropItemDialogState
import mosis.streetsandtotems.feature_backpack.presentation.ResourceType
import mosis.streetsandtotems.ui.theme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropItemDialog(state: DropItemDialogState, onDismissRequest: () -> Unit) {
    lateinit var dialogFocusManager: FocusManager
    val dropAmount = remember { mutableStateOf(FormFieldConstants.DEFAULT_AMOUNT) }
    if (state.open)
        AlertDialog(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) { dialogFocusManager.clearFocus() }
                .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
            onDismissRequest = onDismissRequest,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.tertiaryContainer,
                                CircleShape
                            )
                    ) {
                        if (state.dropTotem) {
                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                                contentDescription = ImageContentDescriptionConstants.TOTEM,
                                modifier = Modifier
                                    .size(MaterialTheme.sizes.drop_item_dialog_icon_size)
                            )
                        } else if (state.itemType != null) {
                            Icon(
                                imageVector = when (state.itemType) {
                                    ResourceType.Emerald -> ImageVector.vectorResource(id = R.drawable.emerald)
                                    ResourceType.Wood -> ImageVector.vectorResource(id = R.drawable.wood)
                                    ResourceType.Stone -> ImageVector.vectorResource(id = R.drawable.stone)
                                    ResourceType.Brick -> ImageVector.vectorResource(id = R.drawable.brick)
                                },
                                contentDescription = ImageContentDescriptionConstants.EDIT_PASSWORD,
                                modifier = Modifier
                                    .size(MaterialTheme.sizes.drop_item_dialog_icon_size)
                                    .padding(MaterialTheme.sizes.drop_item_dialog_icon_padding),
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (state.dropTotem) {
                            Text(
                                text = ItemsConstants.TOTEM,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else if (state.itemType != null) Text(
                            text = when (state.itemType) {
                                ResourceType.Emerald -> ItemsConstants.EMERALD
                                ResourceType.Wood -> ItemsConstants.WOOD
                                ResourceType.Stone -> ItemsConstants.STONE
                                ResourceType.Brick -> ItemsConstants.BRICK
                            },
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (state.itemCount != null) {
                            Text(
                                text = state.itemCount.toString() + ItemsConstants.ITEMS_LEFT,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            },
            text = {
                dialogFocusManager = LocalFocusManager.current
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    CustomButton(
                        clickHandler = { /*TODO*/ },
                        text = ButtonConstants.DROP,
                        buttonType = CustomButtonType.Outlined,
                        textStyle = MaterialTheme.typography.titleMedium,
                        enabled = dropAmount.value != "" && if (state.itemCount != null) state.itemCount >= dropAmount.value.toInt() else true
                    )
                    CustomTextField(
                        modifier = Modifier
                            .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                            .padding(start = MaterialTheme.sizes.drop_item_dialog_spacer),
                        value = dropAmount.value,
                        onValueChange = { dropAmount.value = it },
                        placeholder = FormFieldConstants.AMOUNT,
                        label = FormFieldConstants.AMOUNT,
                        textFieldType = CustomTextFieldType.Outlined,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                    )
                }
            },
            confirmButton = {
                if (state.dropTotem)
                    CustomButton(
                        clickHandler = { /*TODO*/ },
                        text = ButtonConstants.PLACE,
                        buttonType = CustomButtonType.Outlined,
                        textStyle = MaterialTheme.typography.titleMedium,
                        matchParentWidth = true
                    )
            },
        )
}


