package com.bft.ebs.services

import com.bft.ebs.dto.*
import com.bft.ebs.models.*
import java.sql.Date

class EbsMapper {
    companion object {
        fun mapAuthorToAuthorDto(author: Author): AuthorDto {
            return AuthorDto(
                id = author.id,
                name = author.name,
                nickname = author.nickname,
                birthday = author.birthday
            )
        }

        fun mapAuthorDtoToAuthor(authorDto: AuthorDto): Author {
            return Author(
                id = authorDto.id,
                name = authorDto.name ?: "NULL",
                nickname = authorDto.nickname ?: "NULL",
                birthday = authorDto.birthday
            )
        }

        fun mapGenreToGenreDto(genre: Genre): GenreDto {
            return GenreDto(
                id = genre.id,
                name = genre.name
            )
        }

        fun mapGenreDtoToGenre(genreDto: GenreDto): Genre {
            return Genre(
                id = genreDto.id,
                name = genreDto.name ?: "NULL"
            )
        }

        fun mapReaderToReaderDto(reader: Reader): ReaderDto {
            return ReaderDto(
                id = reader.id,
                name = reader.name,
                email = reader.email
            )
        }

        fun mapReaderDtoToReader(readerDto: ReaderDto): Reader {
            return Reader(
                id = readerDto.id,
                name = readerDto.name ?: "NULL",
                email = readerDto.email ?: "NULL"
            )
        }

        fun mapStoryToStoryDto(story: Story): StoryDto {
            return StoryDto(
                id = story.id,
                authors = story.authors?.map { mapAuthorToAuthorDto(it) },
                genre = mapGenreToGenreDto(story.genre),
                name = story.name
            )
        }

        fun mapStoryDtoToStory(storyDto: StoryDto): Story {
            return Story(
                id = storyDto.id,
                authors = storyDto.authors?.map { mapAuthorDtoToAuthor(it) },
                genre = storyDto.genre?.let { mapGenreDtoToGenre(it) } ?: Genre(null, "NULL"),
                name = storyDto.name ?: "NULL"
            )
        }

        fun mapBookToBookDto(book: Book): BookDto {
            return BookDto(
                id = book.id,
                stories = book.stories?.map { mapStoryToStoryDto(it) },
                name = book.name,
                isbn = book.isbn,
                publicationDate = book.publicationDate
            )
        }

        fun mapBookDtoToBook(bookDto: BookDto): Book {
            return Book(
                id = bookDto.id,
                stories = bookDto.stories?.map { mapStoryDtoToStory(it) },
                name = bookDto.name ?: "NULL",
                isbn = bookDto.isbn ?: "NULL",
                publicationDate = bookDto.publicationDate ?: Date(java.util.Date().time)
            )
        }

        fun mapBookInLibToBookInLibDto(bookInLib: BookInLib): BookInLibDto {
            return BookInLibDto(
                id = bookInLib.id,
                book = mapBookToBookDto(bookInLib.book)
            )
        }

        fun mapBookInLibDtoToBookInLib(bookInLibDto: BookInLibDto): BookInLib {
            return BookInLib(
                id = bookInLibDto.id,
                book = bookInLibDto.book?.let { mapBookDtoToBook(it) }
                    ?: mapBookDtoToBook(BookDto(null, null, null, null, null))
            )
        }

        fun mapJournalToJournalDto(journal: Journal): JournalDto {
            return JournalDto(
                id = journal.id,
                book = mapBookInLibToBookInLibDto(journal.book),
                reader = mapReaderToReaderDto(journal.reader),
                startDate = journal.startDate,
                endDate = journal.endDate
            )
        }

        fun mapJournalDtoToJournal(journalDto: JournalDto): Journal {
            return Journal(
                id = journalDto.id,
                book = mapBookInLibDtoToBookInLib(journalDto.book),
                reader = mapReaderDtoToReader(journalDto.reader),
                startDate = journalDto.startDate ?: Date(java.util.Date().time),
                endDate = journalDto.endDate
            )
        }

        fun mapBookInLibIdAndReaderIdToJournalDto(
            bookInLibId: Long,
            readerId: Long,
            startDate: Date = Date(java.util.Date().time)
        ): Journal {
            return mapJournalDtoToJournal(
                JournalDto(
                    null,
                    BookInLibDto(bookInLibId, null),
                    ReaderDto(readerId, null, null),
                    startDate,
                    null
                )
            )
        }
    }
}