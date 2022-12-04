package day03

import readInput

/**
 * Answers from [Advent of Code 2022 Day 3 | Kotlin](https://youtu.be/IPLfo4zXNjk)
 */
fun main() {
    fun part1(input: List<String>): Int {
        val sharedItems = input.map {
            val first = it.substring(0 until it.length / 2).toSet()
            val second = it.substring(it.length / 2).toSet()
            (first intersect second).single()
        }
        return sharedItems.sumOf { it.priority }
    }

    fun part2(input: List<String>): Int {
        return input.size
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
        return if (isLowerCase()) {
            this - 'a' + 1
        } else {
            this - 'A' + 27
        }
    }