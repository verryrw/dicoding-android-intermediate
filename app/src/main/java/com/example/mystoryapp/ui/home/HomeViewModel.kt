package com.example.mystoryapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mystoryapp.data.StoryRepository
import com.example.mystoryapp.di.Injection

class HomeViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun getAllStories(token: String) = storyRepository.getAllStories(token).cachedIn(viewModelScope)
}

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(Injection.provideStoryRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}