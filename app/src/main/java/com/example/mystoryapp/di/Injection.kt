package com.example.mystoryapp.di

import android.content.Context
import com.example.mystoryapp.data.AuthRepository
import com.example.mystoryapp.data.StoryRepository
import com.example.mystoryapp.data.local.room.StoryDatabase
import com.example.mystoryapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideAuthRepository(): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository(apiService)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getInstance(context)
        return StoryRepository(database, apiService)
    }
}