package com.syndicate.parkingapp.view_model.change_password_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.parkingapp.data.model.ChangePasswordState
import com.syndicate.parkingapp.domain.repository.ParkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: ParkingRepository
): ViewModel() {

    val state = MutableStateFlow(ChangePasswordState())

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.SendCodeToEmail -> {
                sendCode(event.email)
            }

            is ChangePasswordEvent.CheckCodeFromEmail -> {
                checkCodeFromEmail(event.code)
            }

            is ChangePasswordEvent.ChangePassword -> {
                changePassword(event.password)
            }
        }
    }

    private fun sendCode(email: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val status = repository.sendCode(email)

            Log.d("changePassword", status.toString())

            if (status) {
                state.update { it.copy(
                    email = email,
                    sendCode = true
                ) }
            }
        }
    }

    private fun checkCodeFromEmail(code: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val status = repository.checkCodeFromEmail(state.value.email, code)

            Log.d("changePassword", status.toString())

            if (status) {
                state.update { it.copy(
                    code = code,
                    checkCode = true
                ) }
            }
        }
    }

    private fun changePassword(password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val status = repository.changePassword(
                state.value.email,
                state.value.code,
                password
            )

            Log.d("changePassword", status.toString())

            if (status) {
                state.update { it.copy(
                    enter = true
                ) }
            }
        }
    }
}