package com.example.mystoryapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class StoryLocationResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<StoryLocation>,
    @SerializedName("message")
    val message: String
)

data class StoryLocation(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String
)