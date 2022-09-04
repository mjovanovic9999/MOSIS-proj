package mosis.streetsandtotems.feature_backpack.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.core.presentation.components.IconType
import javax.inject.Inject

@HiltViewModel
class BackpackViewModel @Inject constructor() : ViewModel() {
    private val _dropItemDialogOpen = mutableStateOf(DropItemDialogState(false))
    val dropItemDialogOpen: State<DropItemDialogState> = _dropItemDialogOpen

    fun openDropItemDialog(
        itemCount: Int,
        resourceType: IconType.ResourceType? = null,
        dropTotem: Boolean = false,
    ) {
        _dropItemDialogOpen.value = _dropItemDialogOpen.value.copy(
            open = true,
            itemType = resourceType,
            itemCount = itemCount,
            dropTotem = dropTotem,
        )
    }

    fun closeDropItemDialog() {
        _dropItemDialogOpen.value = _dropItemDialogOpen.value.copy(
            open = false,
            itemType = null,
            itemCount = null,
            dropTotem = false,
        )
    }

}