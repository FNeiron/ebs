package com.bft.ebs.services.interfaces

import com.bft.ebs.dto.AuthorDto
import com.bft.ebs.dto.StoryDto

interface StoryService {
    fun getStory(id: Long): StoryDto
    fun getAllStories(): List<StoryDto>
    fun createStory(storyDto: StoryDto): Long
    fun deleteStory(id: Long): Boolean
    fun updateStory(storyDto: StoryDto): Boolean
    fun getAuthorsByStoryId(storyId: Long): List<AuthorDto>
}