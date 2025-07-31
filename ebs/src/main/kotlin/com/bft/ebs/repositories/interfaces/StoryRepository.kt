package com.bft.ebs.repositories.interfaces

import com.bft.ebs.models.Author
import com.bft.ebs.models.Story

interface StoryRepository : Repository<Story, Long>{
    fun findAuthorsByStoryId(storyId: Long): List<Author>
}