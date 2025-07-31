package com.bft.ebs.services.impl

import com.bft.ebs.dto.AuthorDto
import com.bft.ebs.dto.StoryDto
import com.bft.ebs.repositories.impl.StoryRepository
import com.bft.ebs.services.EbsMapper
import com.bft.ebs.services.interfaces.StoryService
import org.springframework.stereotype.Service

@Service
class StoryService(private val storyRepository: StoryRepository) :
    StoryService {
    override fun getStory(id: Long): StoryDto {
        return EbsMapper.mapStoryToStoryDto(storyRepository.read(id))
    }

    override fun getAllStories(): List<StoryDto> {
        return storyRepository.findAll().map {EbsMapper.mapStoryToStoryDto((it))
        }
    }

    override fun createStory(storyDto: StoryDto): Long {
        return storyRepository.create(EbsMapper.mapStoryDtoToStory(storyDto)).id ?: throw IllegalStateException("Creation error")
    }

    override fun deleteStory(id: Long): Boolean {
        return storyRepository.delete(id)
    }

    override fun updateStory(storyDto: StoryDto): Boolean {
        return storyRepository.update(EbsMapper.mapStoryDtoToStory(storyDto))
    }

    override fun getAuthorsByStoryId(storyId: Long): List<AuthorDto> {
        return storyRepository.findAuthorsByStoryId(storyId).map { EbsMapper.mapAuthorToAuthorDto(it) }
    }
}