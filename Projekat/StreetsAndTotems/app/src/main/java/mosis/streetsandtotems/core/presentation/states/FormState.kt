package mosis.streetsandtotems.core.presentation.states

import androidx.compose.foundation.rememberScrollState
import com.dsc.form_builder.FormState
import mosis.streetsandtotems.core.presentation.components.form.formfields.FormField
import kotlin.reflect.KClass

class FormState<T : Any>(val fields: List<FormField>, private val dataType: KClass<T>) {
    private val formState = FormState(fields = buildList { fields.forEach { add(it.fieldState) } })

    fun validate(): Boolean {
        return formState.validate()
    }

    fun getDataWithValidation(): T? {
        if(validate())
            return formState.getData(dataType)
        return null
    }

    fun getData(): T{
        return formState.getData(dataType)
    }
}