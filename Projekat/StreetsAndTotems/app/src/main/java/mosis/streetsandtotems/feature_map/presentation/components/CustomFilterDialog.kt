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
import mosis.streetsandtotems.feature_map.domain.util.PinTypes

@Composable
fun CustomFilterDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    filterState: State<Set<PinTypes>>,
    updateFilter: (PinTypes) -> Unit,
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
                        filterState.value.contains(PinTypes.TypeTiki),
                        { updateFilter(PinTypes.TypeTiki) },
                        TOTEMS
                    )
                    CustomFilterChip(
                        filterState.value.contains(PinTypes.TypeFriend),
                        { updateFilter(PinTypes.TypeFriend) },
                        FRIENDS
                    )
                    CustomFilterChip(
                        filterState.value.contains(PinTypes.TypeResource),
                        { updateFilter(PinTypes.TypeResource) },
                        RESOURCES
                    )
                }
                Divider()
            }
        },
        confirmButtonEnabled = true,
        confirmButtonText = "Apply filter",
        onConfirmButtonClick = {
            onConfirmButtonClick()
        },
        dismissButtonEnabled = true,
        dismissButtonText = "Dismiss",
        onDismissButtonClick = { onDismissRequest() },
    )


}