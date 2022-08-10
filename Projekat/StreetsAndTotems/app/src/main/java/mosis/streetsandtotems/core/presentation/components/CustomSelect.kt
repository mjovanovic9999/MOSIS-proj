package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.toSize

@Composable
fun CustomSelect(
    selectList: List<String>,
    label: String,
    type: CustomTextFieldType = CustomTextFieldType.Basic,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    defaultSelectedIndex: Int? = null,
    height: Dp? = null
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedText =
        remember { mutableStateOf(if ((defaultSelectedIndex != null) && (selectList.size > defaultSelectedIndex) && (defaultSelectedIndex >= 0)) selectList[defaultSelectedIndex] else "") }
    val textFieldSize = remember { mutableStateOf(Size.Zero) }
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned { coordinates ->
            textFieldSize.value = coordinates.size.toSize()
        }


    Column(modifier = modifier) {
        CustomTextField(
            value = selectedText.value,
            onValueChange = { selectedText.value = it },
            textFieldType = type,
            readOnly = readOnly,
            label = label,
            trailingIcon = if (expanded.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
            onTrailingIconClicked = { expanded.value = !expanded.value },
            singleLine = true,
            modifier = if (height != null) textFieldModifier.height(height) else textFieldModifier
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.width(with(
                LocalDensity.current
            ) { textFieldSize.value.width.toDp() })
        ) {
            selectList.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = { selectedText.value = it; expanded.value = false })
            }
        }
    }
}