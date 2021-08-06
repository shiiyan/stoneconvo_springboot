package com.stoneconvo.common.domain

abstract class Entity<T>(
    open val id: ValueObject
) {
    abstract class Dto(open val id: String)

    abstract fun toDto(): T
}
