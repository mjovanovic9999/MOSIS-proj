package mosis.streetsandtotems.feature_auth.presentation.components

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    val formState = FormState(
        fields = listOf(
            ChoiceState(
                initial = "Male",
                name = "gender",
                validators = listOf(Validators.Required(message = "you need to specify your gender"))
            ),
            TextFieldState(
                name = "email",
                initial = "name@mail.com",
                transform = { it.trim().lowercase() },
                validators = listOf(Validators.Email()),
            ),
            TextFieldState(
                name = "password",
                validators = listOf(Validators.Required())
            ),
            TextFieldState(
                name = "age",
                initial = "18",
                transform = { it.toInt() },
                validators = listOf(Validators.MinValue(limit = 18, message = "too young"))
            ),
            TextFieldState(
                name = "happiness",
                transform = { it.toFloat() },
                validators = listOf(Validators.Required(message = "how happy are you?"))
            ),
            SelectState(
                initial = mutableListOf("Chess"),
                name = "hobbies",
                validators = listOf(
                    Validators.Required(message = "pick at least one hobby")
                )
            ),
        )
    )


    fun submit() {
        if (formState.validate()) {
            val data = formState.getData(Credentials::class)
            Log.d("Data", "submit: data from the form $data")
        }
    }

}


data class Credentials(
    val email: String,
    val password: String,
    val age: Int,
    val gender: String,
    val happiness: Float,
    val hobbies: List<String>
)