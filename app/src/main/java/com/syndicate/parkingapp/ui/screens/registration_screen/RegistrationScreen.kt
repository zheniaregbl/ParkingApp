package com.syndicate.parkingapp.ui.screens.registration_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.syndicate.parkingapp.R
import com.syndicate.parkingapp.data.model.EnterType
import com.syndicate.parkingapp.data.model.InputFieldType
import com.syndicate.parkingapp.ui.screens.common_components.InputField
import com.syndicate.parkingapp.ui.screens.common_components.SimpleButton
import com.syndicate.parkingapp.ui.theme.GreenButton
import com.syndicate.parkingapp.view_model.registration_view_model.RegistrationEvent
import com.syndicate.parkingapp.view_model.registration_view_model.RegistrationViewModel
import kotlinx.coroutines.delay

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    enterType: EnterType = EnterType.AUTHORIZATION,
    navigateToMap: () -> Unit = { },
    navigateToChangePassword: () -> Unit = { }
) {
    val viewModel = hiltViewModel<RegistrationViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state) {
        if (state.enter) {
            delay(250)
            navigateToMap()
        }
    }

    var stateScreen by remember {
        mutableStateOf(enterType)
    }

    val emailText = remember {
        mutableStateOf("")
    }
    val passwordText = remember {
        mutableStateOf("")
    }
    val againPasswordText = remember {
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
                    .height(40.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                InputField(
                    textState = emailText,
                    hintText = "Email",
                    isErrorState = errorEmail,
                    inputType = InputFieldType.EMAIL
                )

                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )

                InputField(
                    textState = passwordText,
                    hintText = "Пароль",
                    isErrorState = errorPassword,
                    inputType = InputFieldType.PASSWORD
                )

                AnimatedVisibility(
                    visible = stateScreen == EnterType.REGISTRATION
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                        )

                        InputField(
                            textState = againPasswordText,
                            hintText = "Повторите пароль",
                            isErrorState = errorAgainPassword,
                            inputType = InputFieldType.PASSWORD
                        )

                        Spacer(
                            modifier = Modifier
                                .height(38.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = stateScreen == EnterType.AUTHORIZATION
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                        )

                        Text(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { navigateToChangePassword() },
                            text = "Восстановить",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = GreenButton
                        )

                        Spacer(
                            modifier = Modifier
                                .height(15.dp)
                        )

                        SimpleButton(
                            text = "Войти",
                            onClick = {
                                if (emailText.value.isNotEmpty() && passwordText.value.isNotEmpty() &&
                                        !errorEmail.value &&
                                        !errorPassword.value
                                    )
                                    viewModel.onEvent(RegistrationEvent.Authorization(
                                        emailText.value,
                                        passwordText.value
                                    ))
                            }
                        )

                        Spacer(
                            modifier = Modifier
                                .height(15.dp)
                        )
                    }
                }

                SimpleButton(
                    text = "Зарегистрироваться",
                    onClick = {
                        if (stateScreen == EnterType.AUTHORIZATION) {
                            stateScreen = EnterType.REGISTRATION
                            emailText.value = ""
                            passwordText.value = ""
                        } else {
                            if (passwordText.value == againPasswordText.value &&
                                    passwordText.value.isNotEmpty() &&
                                    emailText.value.isNotEmpty() &&
                                    !errorEmail.value &&
                                    !errorPassword.value
                                )
                                viewModel.onEvent(RegistrationEvent.Registration(
                                    emailText.value,
                                    passwordText.value
                                ))
                        }
                    }
                )

                AnimatedVisibility(
                    visible = stateScreen == EnterType.REGISTRATION
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(15.dp)
                        )

                        SimpleButton(
                            text = "Назад к авторизации",
                            onClick = {
                                stateScreen = EnterType.AUTHORIZATION
                                emailText.value = ""
                                passwordText.value = ""
                                againPasswordText.value = ""
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewRegistrationScreen() {
    RegistrationScreen(
        modifier = Modifier
            .fillMaxSize(),
        enterType = EnterType.REGISTRATION
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewAuthScreen() {
    RegistrationScreen(
        modifier = Modifier
            .fillMaxSize(),
        enterType = EnterType.AUTHORIZATION
    )
}