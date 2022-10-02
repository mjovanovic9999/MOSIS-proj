package mosis.streetsandtotems.feature_totems.presentation

sealed class TotemViewModelEvents {
    data class ShowDialog(val index: Int) : TotemViewModelEvents()
    object CloseDialog : TotemViewModelEvents()
    object RemoveCallbacks : TotemViewModelEvents()
    object SelectTotem : TotemViewModelEvents()
}
