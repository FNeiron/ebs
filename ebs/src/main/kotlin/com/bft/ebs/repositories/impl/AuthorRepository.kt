package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Author
import com.bft.ebs.repositories.interfaces.AuthorRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository

@Repository
class AuthorRepository(private val jdbcTemplate: JdbcTemplate) :
    AuthorRepository {

    override fun create(entity: Author): Author {
        // SQL запрос на вставку с возвратом сгенерированного id
        val sql = "INSERT INTO authors (name, nickname, birthday) VALUES (?, ?, ?) RETURNING id"

        // Получаем сгенерированный ID после вставки
        val generatedId: Long = jdbcTemplate.queryForObject(sql,
            entity.name,
            entity.nickname,
            entity.birthday ?: "NULL") { rs, _ ->
            rs.getLong("id")
        }

        return entity.copy(id=generatedId)
    }

    override fun read(id: Long): Author {
        val sql = "SELECT * FROM authors WHERE id = ?"
        return jdbcTemplate.queryForObject(sql, id) { rs, _ ->
            Author(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                nickname = rs.getString("nickname"),
                birthday = rs.getDate("birthday")
            )
        }
    }

    override fun update(entity: Author): Boolean {
        val sql = "UPDATE authors SET name = ?, nickname = ?, birthday = ? WHERE id = ?"
        return jdbcTemplate.update(sql, entity.name, entity.nickname, entity.birthday, entity.id) > 0
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM authors WHERE id = ?"
        return jdbcTemplate.update(sql, id) > 0
    }

    override fun findAll(): List<Author> {
        val sql = "SELECT * FROM authors ORDER BY id"
        return jdbcTemplate.query(sql) { rs, _ ->
            Author(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                nickname = rs.getString("nickname"),
                birthday = rs.getDate("birthday")
            )
        }
    }

}