package com.example.mystoryapp.ui.home.maps

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.data.StoryRepository
import com.example.mystoryapp.di.Injection

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getAllStoriesWithLocation(token: String) =
        storyRepository.getAllStoriesWithLocation("Bearer $token")
}

class MapsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapsViewModel(Injection.provideStoryRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}