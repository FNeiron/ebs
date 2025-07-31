package com.bft.ebs.controllers

import com.bft.ebs.dto.BookInLibDto
import com.bft.ebs.services.impl.BookInLibService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/books_in_lib")
class BookInLibController(private val bookInLibService: BookInLibService) {

    @GetMapping("/get/{id}")
    fun getBookInLib(@PathVariable("id") id: Long): ResponseEntity<BookInLibDto> {
        return try {
            ResponseEntity(bookInLibService.getBookInLibrary(id), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all")
    fun getAllBookInLib(): ResponseEntity<List<BookInLibDto>> {
        return try {
            ResponseEntity(bookInLibService.getAllBooksInLibrary(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/create")
    fun createBookInLib(@RequestBody bookInLibDto: BookInLibDto): ResponseEntity<BookInLibDto> {
        return try {
            ResponseEntity(bookInLibDto.copy(id = bookInLibService.createBookInLibrary(bookInLibDto)), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/update/{id}")
    fun updateBookInLib(@PathVariable("id") id: Long, @RequestBody bookInLibDto: BookInLibDto): ResponseEntity<Void> {
        return try {
            bookInLibService.updateBookInLibrary(bookInLibDto.copy(id = id))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteBookInLib(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return try {
            bookInLibService.deleteBookInLibrary(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}