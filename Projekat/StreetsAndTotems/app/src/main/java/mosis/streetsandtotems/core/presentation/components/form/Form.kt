package mosis.streetsandtotems.core.presentation.components.form

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.core.presentation.states.FormState


@Composable
fun <T : Any> Form(
    formState: FormState<T>,
    spacing: Dp = 5.dp,
    modifier: Modifier = Modifier.fillMaxWidth(),
    scrollState: ScrollState = rememberScrollState(),

) {

    Column(
        modifier = modifier.verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        formState.fields.forEach {
            it.Content()
        }
    }
}