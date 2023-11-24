package com.syndicate.parkingapp.ui.screens.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.parkingapp.data.model.InputFieldType
import com.syndicate.parkingapp.ui.theme.GreenButton
import com.syndicate.parkingapp.ui.theme.TextFieldContainer
import com.syndicate.parkingapp.ui.theme.TextHintColor
import com.syndicate.parkingapp.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    textState: MutableState<String> = mutableStateOf(""),
    hintText: String = "Почта",
    isErrorState: MutableState<Boolean> = mutableStateOf(false),
    inputType: InputFieldType = InputFieldType.EMAIL
) {
    isErrorState.value = when (inputType) {
        InputFieldType.EMAIL -> !isValidEmail(textState.value)
        InputFieldType.PASSWORD -> !regexValidatePassword(textState.value)
        InputFieldType.CODE -> false
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = textState.value,
        onValueChange = {
            textState.value = it
        },
        placeholder = {
            Text(
                text = hintText,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextHintColor
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(5.dp),
        textStyle = TextStyle(
            fontFamily = fontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black.copy(alpha = 0.8f)
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Red,
            cursorColor = GreenButton,
            errorCursorColor = Color.Red,
            selectionColors = TextSelectionColors(
                handleColor = GreenButton,
                backgroundColor = GreenButton.copy(alpha = 0.4f)
            ),
            containerColor = TextFieldContainer,
            errorContainerColor = TextFieldContainer
        ),
        isError = isErrorState.value,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (inputType) {
                InputFieldType.EMAIL -> KeyboardType.Email
                InputFieldType.PASSWORD -> KeyboardType.Password
                InputFieldType.CODE -> KeyboardType.Number
            }
        )
    )
}

private fun isValidEmail(email: String): Boolean {
    if (email.isEmpty())
        return true

    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}

private fun validatePassword(text: String): Boolean = text.contains(' ') || text.contains('\\')
        || text.contains('\'') || text.contains('\"')

private fun regexValidatePassword(text: String): Boolean {
    if (text.isEmpty())
        return true

    val numberRegex = ".*[0-9].*".toRegex()
    val letterRegex = ".*[A-Z].*[a-z].*".toRegex()
    val symbolRegex = ".*[!?@#\$%^&*_()+{}\\[\\]].*".toRegex()

    return text.matches(numberRegex) && text.matches(letterRegex) && text.matches(symbolRegex)
            && !validatePassword(text)
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewInputField() {
    InputField()
}