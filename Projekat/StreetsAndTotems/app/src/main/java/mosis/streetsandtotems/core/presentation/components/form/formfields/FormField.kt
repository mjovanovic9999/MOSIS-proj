package mosis.streetsandtotems.core.presentation.components.form.formfields

import androidx.compose.runtime.Composable
import com.dsc.form_builder.BaseState

interface FormField {
    val fieldState: BaseState<out Any>;

    @Composable
    fun Content()
}

