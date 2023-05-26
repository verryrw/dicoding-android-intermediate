package com.example.mystoryapp.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.data.AuthRepository
import com.example.mystoryapp.data.local.sharedpreference.AuthPreferences
import com.example.mystoryapp.di.Injection

class LoginViewModel(
    private val application: Application, private val authRepository: AuthRepository
) : ViewModel() {
    fun login(email: String, password: String) =
        authRepository.login(email, password)

    fun register(name: String, email: String, password: String) =
        authRepository.register(name, email, password)

    fun setSessionAndToken(isLoggedIn: Boolean, token: String) {
        val authPreference = AuthPreferences(application.applicationContext)
        authPreference.setSessionAndToken(isLoggedIn, token)
    }
}

class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application, Injection.provideAuthRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}