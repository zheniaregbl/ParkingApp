package com.syndicate.parkingapp.data.repository

import com.syndicate.parkingapp.data.model.RegistrationToken
import com.syndicate.parkingapp.data.remote.ParkingApi
import com.syndicate.parkingapp.domain.repository.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class ParkingRepositoryImpl @Inject constructor(
    private val parkingApi: ParkingApi
): ParkingRepository {

    override suspend fun registration(
        email: String,
        password: String
    ): RegistrationToken = withContext(Dispatchers.Main) {
        val response = parkingApi.registerRequest(email, password)

        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            val statusCode = jsonObject.getInt("status_code")

            if (statusCode == 200)
                RegistrationToken(
                    success = true,
                    token = jsonObject.getString("token")
                )
            else
                RegistrationToken()

        } else RegistrationToken()
    }

    override suspend fun authorization(
        email: String,
        password: String
    ): Boolean = withContext(Dispatchers.Main) {
        val response = parkingApi.authRequest(email, password)

        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            val statusCode = jsonObject.getInt("status_code")

            statusCode == 200

        } else false
    }

    override suspend fun sendCode(email: String): Boolean = withContext(Dispatchers.Main) {
        val response = parkingApi.sendCode(email)

        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            val statusCode = jsonObject.getInt("status_code")

            statusCode == 200

        } else false
    }

    override suspend fun checkCodeFromEmail(
        email: String,
        code: String
    ): Boolean = withContext(Dispatchers.Main) {
        val response = parkingApi.checkCodeFromEmail(email, code)

        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            val statusCode = jsonObject.getInt("status_code")

            statusCode == 200

        } else false
    }

    override suspend fun changePassword(
        email: String,
        code: String,
        password: String
    ): Boolean = withContext(Dispatchers.Main) {
        val response = parkingApi.changePassword(email, code, password)

        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            val statusCode = jsonObject.getInt("status_code")

            statusCode == 200

        } else false
    }
}