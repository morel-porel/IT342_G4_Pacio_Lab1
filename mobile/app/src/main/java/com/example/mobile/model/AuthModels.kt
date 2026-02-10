package com.example.mobile.model

data class LoginRequest(val username:String, val password:String)
data class RegisterRequest(
    val username: String,
    val email:String,
    val firstName:String,
    val lastName:String,
    val password: String
)
data class AuthResponse(val token:String, val user: User)
data class User(
    val userId:Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)
