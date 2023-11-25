package com.syndicate.parkingapp.data.remote

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
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

    @GET("parking/get")
    suspend fun getParkingPlaces(): Response<JsonArray>

    @GET("account/get")
    suspend fun getAccountInfo(
        @Query("token") token: String
    ): Response<JsonObject>

    @GET("feedback/get")
    suspend fun getComments(
        @Query("token") token: String,
        @Query("parkingid") parkingId: String
    ): Response<JsonObject>

    @POST("feedback/leave")
    suspend fun sendComment(
        @Query("token") token: String,
        @Query("parkingid") parkingId: String,
        @Query("message") message: String,
        @Query("rating") rating: String
    ): Response<JsonObject>

    @POST("feedback/remove")
    suspend fun removeComment(
        @Query("token") token: String,
        @Query("parkingid") parkingId: String
    ): Response<JsonObject>
}