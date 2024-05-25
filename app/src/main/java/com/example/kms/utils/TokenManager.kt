package com.example.kms.utils

import android.content.Context
import android.content.SharedPreferences


class TokenManager(val context: Context) {
    private val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
    private val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    private var sharedPreferences: SharedPreferences? =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveTokens(accessToken: String?, refreshToken: String) {
        val editor = sharedPreferences!!.edit()
        editor.putString(ACCESS_TOKEN_KEY, accessToken)
        editor.putString(REFRESH_TOKEN_KEY, accessToken)
        editor.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences!!.getString(ACCESS_TOKEN_KEY, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences!!.getString(REFRESH_TOKEN_KEY, null)
    }

    fun deleteAccessToken() {
        val editor = sharedPreferences!!.edit()
        editor.remove(ACCESS_TOKEN_KEY)
        editor.apply()
    }

    fun deleteRefreshToken() {
        val editor = sharedPreferences!!.edit()
        editor.remove(ACCESS_TOKEN_KEY)
        editor.apply()
    }
}