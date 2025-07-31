package com.bft.ebs.controllers

import com.bft.ebs.dto.ReaderDto
import com.bft.ebs.services.impl.ReaderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/readers")
class ReaderController(private val readerService: ReaderService) {

    @GetMapping("/get/{id}")
    fun getReader(@PathVariable id: Long): ResponseEntity<ReaderDto> {
        return try {
            ResponseEntity(readerService.getReader(id), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all")
    fun getAllReaders(): ResponseEntity<List<ReaderDto>> {
        return try {
            ResponseEntity(readerService.getAllReaders(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/create")
    fun createReader(@RequestBody readerDto: ReaderDto): ResponseEntity<ReaderDto> {
        return try {
            ResponseEntity(readerDto.copy(id = readerService.createReader(readerDto)), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/update/{id}")
    fun updateReader(@PathVariable id: Long, @RequestBody readerDto: ReaderDto): ResponseEntity<Void> {
        return try {
            readerService.updateReader(readerDto.copy(id = id))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteReader(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            readerService.deleteReader(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}