package mosis.streetsandtotems.core.domain.util

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class PasswordResetFieldsEmpty(var email: Boolean) : FieldsEmpty {
    override fun setFieldEmpty(name: String, empty: Boolean) {
        when (name) {
            FormFieldNamesConstants.EMAIL -> {
                email = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean = email
}
