package mosis.streetsandtotems.feature_auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType

@Composable
fun AuthScreen(){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        CustomButton(clickHandler = { /*TODO*/ })
        CustomButton(clickHandler = { /*TODO*/ }, buttonType = CustomButtonType.Outlined, text = "Kurc")
        CustomButton(clickHandler = { /*TODO*/ }, buttonType = CustomButtonType.Text, text = "Pozivce", icon = Icons.Filled.Call)
        CustomButton(clickHandler = { /*TODO*/ }, buttonType = CustomButtonType.Text, text = "TEST")
        TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors()) {
            Text(text = "test")
        }
    }
}