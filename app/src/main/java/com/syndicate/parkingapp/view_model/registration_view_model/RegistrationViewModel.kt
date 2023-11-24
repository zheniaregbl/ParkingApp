package com.syndicate.parkingapp.view_model.registration_view_model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.syndicate.parkingapp.data.model.RegistrationState
import com.syndicate.parkingapp.domain.repository.ParkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: ParkingRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    val state = MutableStateFlow(RegistrationState())

    fun onEvent(event: RegistrationEvent) {
        when (event) {

            is RegistrationEvent.Authorization -> authorization(event.email, event.password)

            is RegistrationEvent.Registration -> registration(event.email, event.password)
        }
    }

    private fun registration(email: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val registrationToken = repository.registration(email, password)

            if (registrationToken.success) {
                sharedPreferences.edit().putString("token", registrationToken.token).apply()
                state.update { it.copy(
                    enter = true
                ) }
            } else {
                Log.d("registration", "no")
            }
        }
    }

    private fun authorization(email: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("registration", "${repository.authorization(email, password)}")

            state.update { it.copy(
                enter = repository.authorization(email, password)
            ) }
        }
    }
}