package com.bft.ebs.controllers

import com.bft.ebs.dto.AuthorDto
import com.bft.ebs.services.impl.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/authors")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping("/get/{id}")
    fun getAuthor(@PathVariable("id") id: Long): ResponseEntity<AuthorDto> {
        return try {
            ResponseEntity(authorService.getAuthor(id), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping("/all")
    fun getAllAuthors(): ResponseEntity<List<AuthorDto>> {
        return try {
            ResponseEntity(authorService.getAllAuthors(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    @PostMapping("/create")
    fun createAuthor(@RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        return try {
            ResponseEntity(authorDto.copy(id = authorService.createAuthor(authorDto)), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @PutMapping("/update/{id}")
    fun updateAuthor(@PathVariable("id") id: Long, @RequestBody authorDto: AuthorDto): ResponseEntity<Void> {
        return try {
            authorService.updateAuthor(authorDto.copy(id = id))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteAuthor(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return try {
            authorService.deleteAuthor(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}