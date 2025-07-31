package com.bft.ebs.controllers

import com.bft.ebs.dto.GenreDto
import com.bft.ebs.services.impl.GenreService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/genres")
class GenreController(private val genreService: GenreService) {
    @GetMapping("/get/{id}")
    fun getGenre(@PathVariable("id") id: Long): ResponseEntity<GenreDto> {
        return try {
            ResponseEntity(genreService.getGenre(id), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all")
    fun getAllGenres(): ResponseEntity<List<GenreDto>> {
        return try {
            ResponseEntity(genreService.getAllGenres(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/create")
    fun createGenre(@RequestBody genreDto: GenreDto): ResponseEntity<GenreDto> {
        return try {
            ResponseEntity(genreDto.copy(id = genreService.createGenre(genreDto)), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteGenre(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return try {
            genreService.deleteGenre(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/update/{id}")
    fun updateGenre(@PathVariable("id") id: Long, @RequestBody genreDto: GenreDto): ResponseEntity<Void> {
        return try {
            genreService.updateGenre(genreDto.copy(id = id))
            ResponseEntity(HttpStatus.OK)
        }catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}