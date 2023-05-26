package com.example.mystoryapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystoryapp.data.local.entity.StoryEntity
import com.example.mystoryapp.data.local.room.StoryDatabase
import com.example.mystoryapp.data.remote.Result
import com.example.mystoryapp.data.remote.response.AddStoryResponse
import com.example.mystoryapp.data.remote.response.StoryLocation
import com.example.mystoryapp.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getAllStories(token: String): LiveData<PagingData<StoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(token, storyDatabase, apiService),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getAllStoriesWithLocation(token: String): LiveData<Result<List<StoryLocation>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllStoriesWithLocation(token)
            emit(Result.Success(response.listStory))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addStory(
        token: String,
        imageMultipart: MultipartBody.Part,
        description: RequestBody
    ): LiveData<Result<AddStoryResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.addStory(token, imageMultipart, description)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        private const val TAG = "Story Repository"
    }
}