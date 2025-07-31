package com.bft.ebs.controllers

import com.bft.ebs.dto.JournalDto
import com.bft.ebs.services.impl.JournalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/journal")
class JournalController(private val journalService: JournalService) {

    @GetMapping("/get/{id}")
    fun getJournal(@PathVariable id: Long): ResponseEntity<JournalDto> {
        return try {
            ResponseEntity(journalService.getJournalRecord(id), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all")
    fun getAllJournal(): ResponseEntity<List<JournalDto>> {
        return try {
            ResponseEntity(journalService.getAllJournalRecords(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all-opened")
    fun getAllOpenedJournal(): ResponseEntity<List<JournalDto>> {
        return try {
            ResponseEntity(journalService.getAllOpenedJournalRecords(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/create")
    fun createJournal(@RequestBody journalDto: JournalDto): ResponseEntity<JournalDto> {
        return try {
            ResponseEntity(journalDto.copy(id = journalService.createJournalRecord(journalDto)), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/debt-book/{bookInLibId}/{readerId}")
    fun createJournal(@PathVariable bookInLibId: Long, @PathVariable readerId: Long): ResponseEntity<JournalDto> {
        return try {
            ResponseEntity(journalService.debtBookByBookIdAndReaderId(bookInLibId, readerId), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/update/{id}")
    fun updateJournal(@PathVariable id: Long, @RequestBody journalDto: JournalDto): ResponseEntity<Void> {
        return try {
            journalService.updateJournalRecord(journalDto.copy(id = id))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/return-book/{journalId}")
    fun updateJournal(@PathVariable journalId: Long): ResponseEntity<Void> {
        return try {
            journalService.returnBookByJournalId(journalId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteJournal(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            journalService.deleteJournalRecord(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}