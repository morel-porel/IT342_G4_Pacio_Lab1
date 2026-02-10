package com.example.mobile.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context:Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    fun saveToken(token:String){
        prefs.edit().putString("jwt_token",token).apply()
    }
    fun getToken():String?{
        return prefs.getString("jwt_token",null)
    }
    fun logout(){
        prefs.edit().clear().apply()
    }
}