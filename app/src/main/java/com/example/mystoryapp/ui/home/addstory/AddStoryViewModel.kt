package com.example.mystoryapp.ui.home.addstory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.data.StoryRepository
import com.example.mystoryapp.data.local.sharedpreference.AuthPreferences
import com.example.mystoryapp.di.Injection
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(
    private val application: Application, private val storyRepository: StoryRepository
) : ViewModel() {
    fun addStory(imageMultipart: MultipartBody.Part, description: RequestBody) =
        storyRepository.addStory(getToken(), imageMultipart, description)

    private fun getToken() = "Bearer ${AuthPreferences(application.applicationContext).getToken()}"
}

class AddStoryViewModelFactory(
    private val application: Application,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddStoryViewModel(application, Injection.provideStoryRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}