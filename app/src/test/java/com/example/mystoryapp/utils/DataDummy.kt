package com.example.mystoryapp.utils

import com.example.mystoryapp.data.local.entity.StoryEntity

object DataDummy {
    fun generateDummyStoryEntity(): List<StoryEntity> {
        val items: MutableList<StoryEntity> = arrayListOf()
        for (i in 0..100) {
            val story = StoryEntity(
                i.toString(),
                "Description $i",
                "Story $i",
                "img-$i.jpg"
            )
            items.add(story)
        }
        return items
    }
}