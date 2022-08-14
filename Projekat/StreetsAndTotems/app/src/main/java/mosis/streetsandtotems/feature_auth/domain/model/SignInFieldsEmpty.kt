package mosis.streetsandtotems.feature_auth.domain.model

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class SignInFieldsEmpty(
    var username: Boolean = true,
    var password: Boolean = true
) : FieldsEmpty {
    override fun setFieldEmpty(name: String, empty: Boolean) {
        when (name) {
            FormFieldNamesConstants.USERNAME -> {
                username = empty
            }
            FormFieldNamesConstants.PASSWORD -> {
                password = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return username || password
    }
}
