package mosis.streetsandtotems.feature_auth.presentation.util

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class EditPasswordFieldsEmpty(
    var newPassword: Boolean = true,
    var repeatNewPassword: Boolean = true
) : FieldsEmpty {
    override fun setFieldEmpty(name: String, empty: Boolean) {
        when (name) {
            FormFieldNamesConstants.NEW_PASSWORD -> {
                newPassword = empty
            }
            FormFieldNamesConstants.REPEAT_PASSWORD -> {
                repeatNewPassword = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return newPassword || repeatNewPassword
    }

}
