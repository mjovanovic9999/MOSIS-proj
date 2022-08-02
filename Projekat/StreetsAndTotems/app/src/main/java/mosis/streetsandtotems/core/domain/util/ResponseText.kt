package mosis.streetsandtotems.core.domain.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class ResponseText {
    data class DynamicString(val value: String) : ResponseText()

    class StringResource(@StringRes val id: Int, val args: Array<Any> = emptyArray()) :
        ResponseText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id, args)
        }
    }
}
