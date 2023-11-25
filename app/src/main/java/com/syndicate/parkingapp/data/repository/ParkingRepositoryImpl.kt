package com.syndicate.parkingapp.data.repository

import android.util.Log
import com.syndicate.parkingapp.data.model.RegistrationToken
import com.syndicate.parkingapp.data.remote.ParkingApi
import com.syndicate.parkingapp.domain.repository.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
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

        Log.d("registration", response.body().toString())

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
    ): RegistrationToken = withContext(Dispatchers.Main) {
        val response = parkingApi.authRequest(email, password)

        Log.d("registration", response.body().toString())

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

    override suspend fun getParkingPlaces(): JSONArray = withContext(Dispatchers.IO) {
        val response = parkingApi.getParkingPlaces()

        if (response.isSuccessful) JSONArray(response.body().toString()) else JSONArray("")
    }

    override suspend fun getAccountInfo(token: String): JSONObject = withContext(Dispatchers.IO) {
        val response = parkingApi.getAccountInfo(token)

        if (response.isSuccessful) JSONObject(response.body().toString()) else JSONObject("")
    }

    override suspend fun getComments(
        token: String,
        parkingId: Int
    ): JSONObject = withContext(Dispatchers.IO) {
        val response = parkingApi.getComments(token, parkingId.toString())

        if (response.isSuccessful) JSONObject(response.body().toString()) else JSONObject("")
    }

    override suspend fun sendComment(
        token: String,
        parkingId: String,
        message: String,
        rating: String
    ): Boolean = withContext(Dispatchers.IO) {
        val response = parkingApi.sendComment(
            token,
            parkingId,
            message,
            rating
        )

        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            val statusCode = jsonObject.getInt("status_code")

            statusCode == 200

        } else false
    }

    override suspend fun removeComment(
        token: String,
        parkingId: String
    ): Boolean = withContext(Dispatchers.IO) {
        val response = parkingApi.removeComment(
            token,
            parkingId
        )



        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            val statusCode = jsonObject.getInt("status_code")

            Log.d("removeComment", jsonObject.getString("message"))

            statusCode == 200

        } else false
    }
}