package com.bft.ebs.repositories.interfaces

interface Repository<T, K> {
    fun create(entity: T): T
    fun read(id: K): T
    fun update(entity: T): Boolean
    fun delete(id: K): Boolean
    fun findAll(): List<T>
}