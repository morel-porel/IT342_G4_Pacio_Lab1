package com.example.mobile.api

import com.example.mobile.model.AuthResponse
import com.example.mobile.model.LoginRequest
import com.example.mobile.model.RegisterRequest
import com.example.mobile.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<User>
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest):Response<AuthResponse>
    @GET("auth/me")
    suspend fun getProfile(@Header("Authorization")token: String):Response<User>

}