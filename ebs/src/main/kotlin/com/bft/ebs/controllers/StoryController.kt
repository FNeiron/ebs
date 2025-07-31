package com.bft.ebs.controllers

import com.bft.ebs.dto.StoryDto
import com.bft.ebs.services.impl.StoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/stories")
class StoryController(private val storyService: StoryService) {
    @GetMapping("/get/{id}")
    fun getStory(@PathVariable id: Long): ResponseEntity<StoryDto> {
        return try {
            ResponseEntity(storyService.getStory(id), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all")
    fun getAllStories(): ResponseEntity<List<StoryDto>> {
        return try {
            ResponseEntity(storyService.getAllStories(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/create")
    fun createStory(@RequestBody storyDto: StoryDto): ResponseEntity<StoryDto> {
        return try {
            ResponseEntity(storyDto.copy(id=storyService.createStory(storyDto)), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/update/{id}")
    fun updateStory(@PathVariable id: Long, @RequestBody storyDto: StoryDto): ResponseEntity<Void> {
        return try {
            storyService.updateStory(storyDto.copy(id = id))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteStory(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            storyService.deleteStory(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}