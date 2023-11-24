package com.syndicate.parkingapp.view_model.registration_view_model

sealed interface RegistrationEvent {

    data class Registration(
        val email: String,
        val password: String
    ): RegistrationEvent

    data class Authorization(
        val email: String,
        val password: String
    ): RegistrationEvent
}