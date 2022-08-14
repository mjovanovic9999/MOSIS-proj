package mosis.streetsandtotems.core.presentation.states

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.dsc.form_builder.FormState
import mosis.streetsandtotems.core.presentation.components.form.formfields.FormField
import kotlin.reflect.KClass

interface FieldsEmpty {
    fun setFieldEmpty(name: String, empty: Boolean)
    fun anyFieldIsEmpty(): Boolean
}


class FormState<T : Any>(
    val fields: List<FormField>,
    private val dataType: KClass<T>,
    private val initialFieldsEmpty: FieldsEmpty
) {
    private val formState = FormState(fields = buildList { fields.forEach { add(it.fieldState) } })

    private val _isFormFilled = mutableStateOf(!initialFieldsEmpty.anyFieldIsEmpty())
    val isFormFilled: State<Boolean> = _isFormFilled


    fun validate(): Boolean {
        return formState.validate()
    }

    fun validateAndFocusFirstInvalidField(): Boolean {
        val valid = formState.validate()
        if (!valid) fields.first { field -> field.fieldState.hasError }.focusRequester.requestFocus()
        return valid
    }

    fun getDataWithValidation(): T? {
        if (validate())
            return formState.getData(dataType)
        return null
    }

    fun getData(): T {
        return formState.getData(dataType)
    }

    fun onFormFieldValueChange(value: Any, name: String) {
        if (value == "") {
            _isFormFilled.value = false
            initialFieldsEmpty.setFieldEmpty(name, true)
        } else {
            initialFieldsEmpty.setFieldEmpty(name, false)
            _isFormFilled.value = !initialFieldsEmpty.anyFieldIsEmpty()
        }

    }
}