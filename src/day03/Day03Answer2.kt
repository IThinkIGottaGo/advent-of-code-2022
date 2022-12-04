package day03

import readInput

/**
 * Answers from [Advent of Code 2022 Day 3 | Kotlin](https://youtu.be/IPLfo4zXNjk)
 */
fun main() {
    fun part1(input: List<String>): Int {
        val sharedItems = input.map {
            val first = it.substring(0 until it.length / 2)
            val second = it.substring(it.length / 2)
            (first intersect second).single()
        }
        return sharedItems.sumOf { it.priority }
    }

    fun part2(input: List<String>): Int {
        val keycards = input.chunked(3) {
            val (a, b, c) = it
            val keycard = a intersect b intersect c
            keycard.single()
        }
        return keycards.sumOf { it.priority }
    }

    val testInput = readInput("day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day03")
    println(part1(input))   // 8123
    println(part2(input))   // 2620
}

val Char.priority
    get(): Int {
        return when (this) {
            in 'a'..'z' -> this - 'a' + 1
            in 'A'..'Z' -> this - 'A' + 27
            else -> error("Check your input! $this")
        }
    }

// or, for fewer manual call to `.toSet`:
infix fun String.intersect(other: String) = toSet() intersect other.toSet()

infix fun Set<Char>.intersect(other: String) = this intersect other.toSet()