package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Reader
import com.bft.ebs.repositories.interfaces.ReaderRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository

@Repository
class ReaderRepository(private val jdbcTemplate: JdbcTemplate) :
    ReaderRepository {
    override fun create(entity: Reader): Reader {
        val sql = "INSERT INTO readers (name, email) VALUES(?, ?) RETURNING id"

        val generatedId: Long = jdbcTemplate.queryForObject(sql,
            entity.name,
            entity.email) { rs, _ ->
            rs.getLong("id")
        }

        return entity.copy(id=generatedId)
    }

    override fun read(id: Long): Reader {
        val sql = "SELECT * FROM readers WHERE id = ?"
        return jdbcTemplate.queryForObject(sql, id) { rs, _ ->
            Reader(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                email = rs.getString("email")
            )
        }
    }

    override fun update(entity: Reader): Boolean {
        val sql = "UPDATE readers SET name = ?, email = ? WHERE id = ?"
        return jdbcTemplate.update(sql, entity.name, entity.email, entity.id) > 0
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM readers WHERE id = ?"
        return jdbcTemplate.update(sql, id) > 0
    }

    override fun findAll(): List<Reader> {
        val sql = "SELECT * FROM readers"
        return jdbcTemplate.query(sql) { rs, _ ->
            Reader(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                email = rs.getString("email")
            )
        }
    }
}