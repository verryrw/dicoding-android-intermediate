package com.example.mystoryapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mystoryapp.data.remote.Result
import com.example.mystoryapp.data.remote.response.LoginResult
import com.example.mystoryapp.data.remote.response.RegisterResponse
import com.example.mystoryapp.data.remote.retrofit.ApiService

class AuthRepository(private val apiService: ApiService) {
    fun login(email: String, password: String): LiveData<Result<LoginResult>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            val user = response.loginResult
            emit(Result.Success(user))
        } catch (e: Exception) {
            Log.e(TAG, "login: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(
        name: String, email: String, password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "register: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        private const val TAG = "Auth Repository"
    }
}

