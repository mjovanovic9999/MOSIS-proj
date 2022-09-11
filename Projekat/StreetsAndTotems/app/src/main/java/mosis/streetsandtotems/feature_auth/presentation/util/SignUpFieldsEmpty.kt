package mosis.streetsandtotems.feature_auth.presentation.util

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class SignUpFieldsEmpty(
    var firstName: Boolean = true,
    var lastName: Boolean = true,
    var phoneNumber: Boolean = true,
    var email: Boolean = true,
    var password: Boolean = true,
    var repeatPassword: Boolean = true,
    var imageUri: Boolean = true,
    var userName: Boolean = true
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
            FormFieldNamesConstants.EMAIL -> {
                email = empty
            }
            FormFieldNamesConstants.PASSWORD -> {
                password = empty
            }
            FormFieldNamesConstants.REPEAT_PASSWORD -> {
                repeatPassword = empty
            }
            FormFieldNamesConstants.IMAGE_URI -> {
                imageUri = empty
            }
            FormFieldNamesConstants.USER_NAME -> {
                userName = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return firstName || lastName || phoneNumber || email || password || repeatPassword || imageUri || userName
    }
}