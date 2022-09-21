package mosis.streetsandtotems.feature_auth.presentation.util

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class ProfileFieldsEmpty(
    var firstName: Boolean = false,
    var lastName: Boolean = false,
    var phoneNumber: Boolean = false,
    var email: Boolean = false,
    var imagePath: Boolean = false
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
            FormFieldNamesConstants.IMAGE_PATH -> {
                email = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return firstName || lastName || phoneNumber || email || imagePath
    }

}
