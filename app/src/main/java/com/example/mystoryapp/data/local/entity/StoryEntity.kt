package com.example.mystoryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class StoryEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "photoUrl")
    val photoUrl: String
)