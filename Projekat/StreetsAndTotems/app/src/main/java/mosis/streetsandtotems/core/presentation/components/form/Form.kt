package mosis.streetsandtotems.core.presentation.components.form

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun <T : Any> Form(
    formState: FormState<T>,
    spacing: Dp = MaterialTheme.sizes.form_default_spacing,
    modifier: Modifier = Modifier.fillMaxWidth(),
    scrollState: ScrollState = rememberScrollState(),
    asColumn: Boolean = true
) {

    if (asColumn)
        Column(
            modifier = modifier.verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormContent(formState = formState)
        }
    else FormContent(formState = formState)
}

@Composable
private fun <T : Any> FormContent(formState: FormState<T>) {
    formState.fields.forEach {
        it.onValueChanged =
            { value -> formState.onFormFieldValueChange(value, it.fieldState.name) }
        it.Content()
    }
}