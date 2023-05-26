package com.example.mystoryapp.data.local.sharedpreference

import android.content.Context

internal class AuthPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setSessionAndToken(isLoggedIn: Boolean, token: String) {
        val editor = preferences.edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getSession(): Boolean {
        return preferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN, "")
    }

    companion object {
        private const val PREFS_NAME = "auth_pref"
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val TOKEN = "token"
    }
}