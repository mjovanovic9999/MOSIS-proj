package mosis.streetsandtotems.core.presentation.components.form.formfields

import androidx.compose.runtime.Composable
import com.dsc.form_builder.BaseState

abstract class FormField {
    abstract val fieldState: BaseState<out Any>
    abstract var onValueChanged: (Any) -> Unit

    @Composable
    abstract fun Content()

}

