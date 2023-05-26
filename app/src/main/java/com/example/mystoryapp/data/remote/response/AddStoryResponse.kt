package com.example.mystoryapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)
