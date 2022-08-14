package mosis.streetsandtotems.feature_auth.domain.model

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class SignUpFieldsEmpty(
    var firstName: Boolean = true,
    var lastName: Boolean = true,
    var phoneNumber: Boolean = true,
    var username: Boolean = true,
    var password: Boolean = true,
    var repeatPassword: Boolean = true
) : FieldsEmpty {
    override fun setFieldEmpty(name: String, empty: Boolean) {
        when (name) {
            FormFieldNamesConstants.FIRST_NAME -> {
                firstName = empty
            }
            FormFieldNamesConstants.LAST_NAME -> {
                lastName = empty
            }
            FormFieldNamesConstants.PHONE_NUMBER -> {
                phoneNumber = empty
            }
            FormFieldNamesConstants.USERNAME -> {
                username = empty
            }
            FormFieldNamesConstants.PASSWORD -> {
                password = empty
            }
            FormFieldNamesConstants.REPEAT_PASSWORD -> {
                repeatPassword = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return firstName || lastName || phoneNumber || username || password || repeatPassword
    }
}