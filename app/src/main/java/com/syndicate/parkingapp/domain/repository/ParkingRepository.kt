package com.syndicate.parkingapp.domain.repository

import com.syndicate.parkingapp.data.model.RegistrationToken
import org.json.JSONArray
import org.json.JSONObject

interface ParkingRepository {
    suspend fun registration(email: String, password: String): RegistrationToken
    suspend fun authorization(email: String, password: String): RegistrationToken
    suspend fun sendCode(email: String): Boolean
    suspend fun checkCodeFromEmail(email: String, code: String): Boolean
    suspend fun changePassword(email: String, code: String, password: String): Boolean
    suspend fun getParkingPlaces(): JSONArray
    suspend fun getAccountInfo(token: String): JSONObject
    suspend fun getComments(token: String, parkingId: Int): JSONObject
    suspend fun sendComment(token: String, parkingId: String, message: String, rating: String): Boolean
    suspend fun removeComment(token: String, parkingId: String): Boolean
}