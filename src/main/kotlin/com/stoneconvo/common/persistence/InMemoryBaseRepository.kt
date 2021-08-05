package com.stoneconvo.common.persistence

import com.stoneconvo.common.domain.Entity

class InMemoryBaseRepository<T : Entity> {
    private val entities: MutableList<T> = mutableListOf()

    fun reset() {
        entities.clear()
    }

    fun count(): Int = entities.size

    fun findBy(predicate: (T) -> Boolean): T? = entities.find(predicate = predicate)

    fun insert(entity: T) {
        entities.add(entity)
    }

    fun update(entity: T) {
        entities.replaceAll {
            if (it.id == entity.id) entity
            else it
        }
    }
}
