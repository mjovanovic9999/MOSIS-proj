package mosis.streetsandtotems.core.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSelect(
    selectList: List<String>,
    label: String,
    type: CustomTextFieldType = CustomTextFieldType.Basic,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    defaultSelectedIndex: Int? = null,
    height: Dp? = null,
    onIndexChange: (Int) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val expanded = remember { mutableStateOf(false) }
    val selectedText =
        mutableStateOf(if ((defaultSelectedIndex != null) && (selectList.size > defaultSelectedIndex) && (defaultSelectedIndex >= 0)) selectList[defaultSelectedIndex] else "")

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value },
        modifier = modifier
    ) {
        CustomTextField(
            value = selectedText.value,
            onValueChange = { selectedText.value = it },
            textFieldType = type,
            readOnly = readOnly,
            label = label,
            trailingIcon = if (expanded.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
            singleLine = true,
            modifier = if (height != null) Modifier.height(height) else Modifier,
            colors = if (type == CustomTextFieldType.Basic) ExposedDropdownMenuDefaults.textFieldColors() else ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        val filteringOptions =
            selectList.filter { it.contains(selectedText.value, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                selectList.forEachIndexed { index, it ->
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = {
                            selectedText.value = it
                            expanded.value = false
                            focusManager.clearFocus()
                            onIndexChange(index)
                        })
                }
            }
        }
    }
}