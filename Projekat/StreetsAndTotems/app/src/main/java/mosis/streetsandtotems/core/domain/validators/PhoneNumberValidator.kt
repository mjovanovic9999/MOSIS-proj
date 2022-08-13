package mosis.streetsandtotems.core.domain.validators

import mosis.streetsandtotems.core.RegexConstants

fun validatePhoneNumber(value: Any): Boolean {
    val pattern = Regex(RegexConstants.PHONE_NUMBER)
    return pattern.containsMatchIn(value as String)
}

