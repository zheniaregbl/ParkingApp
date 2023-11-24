package com.syndicate.parkingapp.data.remote

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ParkingApi {

    @POST("register")
    suspend fun registerRequest(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<JsonObject>

    @POST("auth")
    suspend fun authRequest(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<JsonObject>

    @POST("recover")
    suspend fun sendCode(
        @Query("email") email: String
    ): Response<JsonObject>

    @POST("recover")
    suspend fun checkCodeFromEmail(
        @Query("email") email: String,
        @Query("recoverycode") code: String,
    ): Response<JsonObject>

    @POST("recover")
    suspend fun changePassword(
        @Query("email") email: String,
        @Query("recoverycode") code: String,
        @Query("password") password: String,
    ): Response<JsonObject>
}