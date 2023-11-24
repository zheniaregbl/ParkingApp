package com.syndicate.parkingapp.ui.screens.change_password_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.syndicate.parkingapp.R
import com.syndicate.parkingapp.data.model.ChangePassword
import com.syndicate.parkingapp.data.model.InputFieldType
import com.syndicate.parkingapp.ui.screens.common_components.InputField
import com.syndicate.parkingapp.ui.screens.common_components.SimpleButton
import com.syndicate.parkingapp.view_model.change_password_view_model.ChangePasswordEvent
import com.syndicate.parkingapp.view_model.change_password_view_model.ChangePasswordViewModel
import kotlinx.coroutines.delay

@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    navigateToRegistration: () -> Unit = { }
) {
    val viewModel = hiltViewModel<ChangePasswordViewModel>()
    val state by viewModel.state.collectAsState()

    var stateScreen by remember {
        mutableStateOf(ChangePassword.ENTER_EMAIL)
    }

    LaunchedEffect(key1 = state) {
        if (state.sendCode) stateScreen = ChangePassword.ENTER_CODE
        if (state.checkCode) stateScreen = ChangePassword.NEW_PASSWORD

        if (state.enter) {
            delay(150)
            navigateToRegistration()
        }
    }

    val emailText = remember {
        mutableStateOf("")
    }
    val codeText = remember {
        mutableStateOf("")
    }
    val newPasswordText = remember {
        mutableStateOf("")
    }
    val newAgainPasswordText = remember {
        mutableStateOf("")
    }

    val errorEmail = remember {
        mutableStateOf(false)
    }
    val errorPassword = remember {
        mutableStateOf(false)
    }
    val errorAgainPassword = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 40.dp
                ),
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_flat),
                    contentDescription = null
                )
            }

            Spacer(
                modifier = Modifier
                    .height(107.dp)
            )

            AnimatedVisibility(
                visible = stateScreen == ChangePassword.ENTER_EMAIL
            ) {
                InputField(
                    textState = emailText,
                    hintText = "Email",
                    isErrorState = errorEmail,
                    inputType = InputFieldType.EMAIL
                )
            }

            AnimatedVisibility(
                visible = stateScreen == ChangePassword.ENTER_CODE
            ) {
                InputField(
                    textState = codeText,
                    hintText = "Код с Email",
                    inputType = InputFieldType.CODE
                )
            }

            AnimatedVisibility(
                visible = stateScreen == ChangePassword.NEW_PASSWORD
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    InputField(
                        textState = newPasswordText,
                        hintText = "Новый пароль",
                        isErrorState = errorPassword,
                        inputType = InputFieldType.PASSWORD
                    )

                    Spacer(
                        modifier = Modifier
                            .height(15.dp)
                    )

                    InputField(
                        textState = newAgainPasswordText,
                        hintText = "Повторите пароль",
                        isErrorState = errorAgainPassword,
                        inputType = InputFieldType.PASSWORD
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            AnimatedContent(
                targetState = stateScreen,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = ""
            ) { animState ->
                SimpleButton(
                    text = when (animState) {
                        ChangePassword.ENTER_EMAIL -> "Получить код"
                        ChangePassword.ENTER_CODE -> "Восстановить"
                        ChangePassword.NEW_PASSWORD -> "Сохранение"
                    },
                    onClick = {
                        when (animState) {
                            ChangePassword.ENTER_EMAIL -> {
                                viewModel.onEvent(ChangePasswordEvent.SendCodeToEmail(emailText.value))
                            }
                            ChangePassword.ENTER_CODE -> {
                                viewModel.onEvent(ChangePasswordEvent.CheckCodeFromEmail(codeText.value))
                            }
                            ChangePassword.NEW_PASSWORD -> {
                                if (newPasswordText.value == newAgainPasswordText.value &&
                                        newPasswordText.value.isNotEmpty()) {

                                    viewModel.onEvent(ChangePasswordEvent.ChangePassword(newPasswordText.value))
                                }

                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewChangePasswordScreen() {
    ChangePasswordScreen()
}