package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.SearchConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.feature_map.domain.model.ResourceType

@Composable
fun CustomSearchDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onUsersSearch: (String, Double) -> Unit,
    onResourcesSearch: (ResourceType, Double) -> Unit
) {
    val selectedTabIndex = remember { mutableStateOf(0) }
    val distance = remember {
        mutableStateOf("0")
    }

    CustomDialog(
        isOpen = isOpen,
        title = {
            Text(
                text = TitleConstants.SEARCH,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = selectedTabIndex.value) {
                    Tab(selected = selectedTabIndex.value == 0,
                        onClick = { selectedTabIndex.value = 0 },
                        text = { Text(SearchConstants.USERS) })
                    Tab(selected = selectedTabIndex.value == 1,
                        onClick = { selectedTabIndex.value = 1 },
                        text = { Text(SearchConstants.RESOURCES) })
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

                    when (selectedTabIndex.value) {
                        0 -> {
                            UsersSearch(onUsersSearch = {
                                onUsersSearch(
                                    it, distance.value.toDouble()
                                )
                            })
                        }
                        1 -> {
                            ResourcesSearch(onResourcesSearch = {
                                onResourcesSearch(
                                    ResourceType.valueOf(
                                        it
                                    ), distance.value.toDouble()
                                )
                            })
                        }
                    }
                    CustomTextField(
                        value = distance.value,
                        onValueChange = { distance.value = it },
                        placeholder = SearchConstants.DISTANCE,
                        label = SearchConstants.DISTANCE,
                        textFieldType = CustomTextFieldType.Outlined,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        singleLine = true
                    )
                }
            }
        },
        onDismissRequest = onDismissRequest,
        dismissButtonVisible = false,
        confirmButtonVisible = false
    )
}

@Composable
private fun UsersSearch(onUsersSearch: (String) -> Unit) {
    val value = remember { mutableStateOf("") }
    CustomTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = FormFieldConstants.USER_NAME,
        placeholder = FormFieldConstants.USER_NAME,
        singleLine = true,
        trailingIcon = Icons.Outlined.Cancel,
        onTrailingIconClicked = { value.value = "" },
        textFieldType = CustomTextFieldType.Outlined
    )

    CustomButton(
        clickHandler = { onUsersSearch(value.value) },
        enabled = value.value != "",
        text = ButtonConstants.SEARCH,
        matchParentWidth = true
    )
}


@Composable
private fun ResourcesSearch(onResourcesSearch: (String) -> Unit) {
    val selectedIndex = remember {
        mutableStateOf<Int?>(null)
    }
    CustomSelect(
        selectList = SearchConstants.selectList,
        label = SearchConstants.RESOURCES,
        onIndexChange = { selectedIndex.value = it },
        type = CustomTextFieldType.Outlined,
        readOnly = true,
        defaultSelectedIndex = selectedIndex.value
    )
    CustomButton(
        clickHandler = { selectedIndex.value?.let { onResourcesSearch(SearchConstants.selectList[it]) } },
        text = ButtonConstants.SEARCH,
        matchParentWidth = true,
        enabled = selectedIndex.value != null
    )

}
