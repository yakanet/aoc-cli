package com.github.yakanet.model

import java.time.LocalDateTime

@JvmInline
value class Year(private val year: Int) {
    init {
        require(year >= 2015)
        require(year <= LocalDateTime.now().year)
    }

    override fun toString(): String {
        return year.toString()
    }
}


@JvmInline
value class Day(private val day: Int) {
    init {
        require(day >= 1)
        require(day <= 25)
    }

    override fun toString() = day.toString()

    fun normalize(): String = day.toString().padStart(2, '0')
}