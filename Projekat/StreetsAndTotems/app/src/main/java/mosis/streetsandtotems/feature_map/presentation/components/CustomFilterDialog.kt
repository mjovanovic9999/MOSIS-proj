package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.core.PinConstants.FRIENDS
import mosis.streetsandtotems.core.PinConstants.RESOURCES
import mosis.streetsandtotems.core.PinConstants.TOTEMS
import mosis.streetsandtotems.core.presentation.components.CustomDialog

@Composable
fun CustomFilterDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    updateFilterResources: () -> Unit,
    updateFilterPlayers: () -> Unit,
    updateFilterTotems: () -> Unit,
    filterResourceState: State<Boolean>,
    filterFriendsState: State<Boolean>,
    filterTotemsState: State<Boolean>,
) {
    CustomDialog(
        modifier = Modifier.fillMaxWidth(),
        isOpen = isOpen,
        title = {
            Text("Filter map pins")
        },
        text = {
            Column {
                Text(text = "Category")
                Row {
                    CustomFilterChip(
                        filterResourceState.value,
                        { updateFilterResources() },
                        RESOURCES
                    )
                    CustomFilterChip(
                        filterFriendsState.value,
                        { updateFilterPlayers() },
                        FRIENDS
                    )
                    CustomFilterChip(
                        filterTotemsState.value,
                        { updateFilterTotems() },
                        TOTEMS
                    )
                }
                Divider()
            }
        },
        confirmButtonEnabled = true,
        confirmButtonText = "Apply",
        onConfirmButtonClick = { onConfirmButtonClick() },
        dismissButtonEnabled = true,
        dismissButtonText = "Dismiss",
        onDismissButtonClick = { onDismissRequest() },
    )


}