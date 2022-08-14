package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomPage(
    titleText: String? = null,
    titleContent: (@Composable () -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit)? = null,
    scrollable: Boolean = false,
    contentVerticalArrangement: Arrangement.Vertical = Arrangement.SpaceEvenly,
    contentMaxWidth: Float = MaterialTheme.sizes.default_form_width
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val boxModifier = if (scrollable) Modifier
        .fillMaxSize()
        .drawVerticalScrollbar(scrollState)
        .verticalScroll(scrollState) else Modifier.fillMaxSize()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { focusManager.clearFocus() }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (titleText != null)
            Text(
                text = titleText,
                style = MaterialTheme.typography.displaySmall.plus(TextStyle(fontWeight = FontWeight.ExtraBold)),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        titleContent?.invoke()
        Box(
            modifier = boxModifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(contentMaxWidth)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = contentVerticalArrangement
            ) {
                content?.invoke(this)
            }
        }
    }
}