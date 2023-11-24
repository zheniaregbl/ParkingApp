package com.syndicate.parkingapp.view_model.change_password_view_model

import com.syndicate.parkingapp.data.model.ChangePassword

sealed interface ChangePasswordEvent {

    data class SendCodeToEmail(
        val email: String
    ): ChangePasswordEvent

    data class CheckCodeFromEmail(
        val code: String
    ): ChangePasswordEvent

    data class ChangePassword(
        val password: String
    ): ChangePasswordEvent
}