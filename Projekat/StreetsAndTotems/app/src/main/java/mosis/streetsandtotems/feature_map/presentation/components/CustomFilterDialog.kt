package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import mosis.streetsandtotems.R
import mosis.streetsandtotems.feature_map.domain.util.PinTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFilterDialog(isOpen: Boolean, onDismissRequest: () -> Unit) {
    val commentState = remember { mutableStateListOf<PinTypes>() }
//il card

    val isSelected = remember {
        mutableStateOf(false)
    }
    if (isOpen)
        FilterChip(
            selected = isSelected.value,
            onClick = { isSelected.value = !isSelected.value },
            label = { Text(text = "cccc") },
            leadingIcon = { Pin(resourceId = R.drawable.tiki) },
            colors = object : SelectableChipColors {
                @Composable
                override fun containerColor(enabled: Boolean, selected: Boolean): State<Color> {
                    return mutableStateOf<Color>(
                        value = Color.White,
                        policy = structuralEqualityPolicy<Color>()
                    )
                }

                @Composable
                override fun labelColor(enabled: Boolean, selected: Boolean): State<Color> {
                    return mutableStateOf<Color>(
                        value = Color.Red,
                        policy = structuralEqualityPolicy<Color>()
                    )
                }

                @Composable
                override fun leadingIconContentColor(
                    enabled: Boolean,
                    selected: Boolean
                ): State<Color> {
                    return mutableStateOf<Color>(
                        value = Color.White,
                        policy = structuralEqualityPolicy<Color>()
                    )
                }

                @Composable
                override fun trailingIconContentColor(
                    enabled: Boolean,
                    selected: Boolean
                ): State<Color> {
                    return mutableStateOf<Color>(
                        value = Color.Green,
                        policy = structuralEqualityPolicy<Color>()
                    )
                }

            }
        )
}