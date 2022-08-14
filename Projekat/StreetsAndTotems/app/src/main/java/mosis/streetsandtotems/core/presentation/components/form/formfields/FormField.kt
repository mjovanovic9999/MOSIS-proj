package mosis.streetsandtotems.core.presentation.components.form.formfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import com.dsc.form_builder.BaseState

abstract class FormField {

    abstract val fieldState: BaseState<out Any>
    var onValueChanged: (Any) -> Unit = {}
    val focusRequester: FocusRequester = FocusRequester()

    @Composable
    abstract fun Content()

}

