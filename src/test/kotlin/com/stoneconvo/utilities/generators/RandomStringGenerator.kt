package com.stoneconvo.utilities.generators

object RandomStringGenerator {
    fun perform(length: Int = 20): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')

        return (1..length).map { allowedChars.random() }.joinToString("")
    }
}
