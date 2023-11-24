package com.syndicate.parkingapp.data.model

data class ChangePasswordState(
    val code: String = "",
    val email: String = "",
    val enter: Boolean = false,
    val sendCode: Boolean = false,
    val checkCode: Boolean = false
)
