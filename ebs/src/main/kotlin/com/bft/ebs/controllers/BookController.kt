package com.bft.ebs.controllers

import com.bft.ebs.dto.BookDto
import com.bft.ebs.services.impl.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/books")
class BookController(private val bookService: BookService) {

    @GetMapping("/get/{id}")
    fun getBook(@PathVariable("id") id: Long): ResponseEntity<BookDto> {
        return try {
            ResponseEntity(bookService.getBook(id), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @GetMapping("/all")
    fun getAllBooks(): ResponseEntity<List<BookDto>> {
        return try {
            ResponseEntity(bookService.getAllBooks(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @PostMapping("/create")
    fun createBook(@RequestBody bookDto: BookDto): ResponseEntity<BookDto> {
        return try {
            ResponseEntity(bookDto.copy(id = bookService.createBook(bookDto)), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/update/{id}")
    fun updateBook(@PathVariable("id") id: Long, @RequestBody bookDto: BookDto): ResponseEntity<Void> {
        return try {
            bookService.updateBook(bookDto.copy(id = id))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteBook(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return try {
            bookService.deleteBook(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}