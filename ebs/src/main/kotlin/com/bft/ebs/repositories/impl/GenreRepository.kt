package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Genre
import com.bft.ebs.repositories.interfaces.GenreRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository

@Repository
class GenreRepository(private val jdbcTemplate: JdbcTemplate) :
    GenreRepository {
    override fun create(entity: Genre): Genre {
        val sql = "INSERT INTO genres (genre) VALUES (?) RETURNING id"

        val generatedId: Long = jdbcTemplate.queryForObject(sql,
            entity.name) { rs, _ ->
            rs.getLong("id")
        }

        return entity.copy(id = generatedId)
    }

    override fun read(id: Long): Genre {
        val sql = "SELECT * FROM genres WHERE id = ?"

        return jdbcTemplate.queryForObject(sql, id) { rs, _ ->
            Genre(
                id = rs.getLong("id"),
                name = rs.getString("genre")
            )
        }
    }

    override fun update(entity: Genre): Boolean {
        val sql = "UPDATE genres SET genre = ? WHERE id = ?"

        return jdbcTemplate.update(sql, entity.name, entity.id) > 0
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM genres WHERE id = ?"

        return jdbcTemplate.update(sql, id) > 0
    }

    override fun findAll(): List<Genre> {
        val sql = "SELECT * FROM genres"

        return jdbcTemplate.query(sql) { rs, _ ->
            Genre(
                id = rs.getLong("id"),
                name = rs.getString("genre")
            )
        }
    }
}