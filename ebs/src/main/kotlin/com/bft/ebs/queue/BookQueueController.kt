package com.bft.ebs.queue

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class BookQueueController(private val bookDispatcher: BookDispatcher) {

    @PutMapping("/queue")
    fun send(@RequestParam bookIsbn: String, @RequestParam readerId: String): ResponseEntity<Void> {
        return try {
            bookDispatcher.sendBook(bookIsbn, readerId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}