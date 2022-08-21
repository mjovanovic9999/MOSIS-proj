package mosis.streetsandtotems.feature_auth.presentation.util

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class SignInFieldsEmpty(
    var email: Boolean = true,
    var password: Boolean = true
) : FieldsEmpty {
    override fun setFieldEmpty(name: String, empty: Boolean) {
        when (name) {
            FormFieldNamesConstants.EMAIL -> {
                email = empty
            }
            FormFieldNamesConstants.PASSWORD -> {
                password = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return email || password
    }
}
