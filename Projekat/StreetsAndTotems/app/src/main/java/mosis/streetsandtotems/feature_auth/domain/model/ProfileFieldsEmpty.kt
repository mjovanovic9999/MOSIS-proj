package mosis.streetsandtotems.feature_auth.domain.model

import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty

data class ProfileFieldsEmpty(
    var firstName: Boolean = false,
    var lastName: Boolean = false,
    var phoneNumber: Boolean = false
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
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return firstName || lastName || phoneNumber
    }

}
