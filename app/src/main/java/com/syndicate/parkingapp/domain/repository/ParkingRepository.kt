package com.syndicate.parkingapp.domain.repository

import com.syndicate.parkingapp.data.model.RegistrationToken

interface ParkingRepository {
    suspend fun registration(email: String, password: String): RegistrationToken
    suspend fun authorization(email: String, password: String): Boolean
    suspend fun sendCode(email: String): Boolean
    suspend fun checkCodeFromEmail(email: String, code: String): Boolean
    suspend fun changePassword(email: String, code: String, password: String): Boolean
}