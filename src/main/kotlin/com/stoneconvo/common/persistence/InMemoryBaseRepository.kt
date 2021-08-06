package com.stoneconvo.common.persistence

import com.stoneconvo.common.domain.Entity

class InMemoryBaseRepository<T : Entity.Dto> {
    private val entityDtos: MutableList<T> = mutableListOf()

    fun reset() {
        entityDtos.clear()
    }

    fun count(): Int = entityDtos.size

    fun findBy(predicate: (T) -> Boolean): T? = entityDtos.find(predicate = predicate)

    fun insert(entity: T) {
        entityDtos.add(entity)
    }

    fun update(entity: T) {
        entityDtos.replaceAll {
            if (it.id == entity.id) entity
            else it
        }
    }
}
