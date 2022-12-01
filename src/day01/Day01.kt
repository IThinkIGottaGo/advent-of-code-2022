package day01

import groupBySkipNullKey
import readInput

var elfIndex = 0

fun main() {
    fun part1(input: List<String>): Int {
        elfIndex = 0
        val results = input.groupBySkipNullKey(
            ::elfIndexOrNull,
            String::toInt,
            ::groupInt,
            Int::plus
        )
        return results.values.maxOrNull()!!
    }

    fun part2(input: List<String>): Int {
        elfIndex = 0
        val results = input.groupBySkipNullKey(
            ::elfIndexOrNull,
            String::toInt,
            ::groupInt,
            Int::plus
        ).values.sortedDescending()
        return results.take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("day01")
    println(part1(input))   // 72718
    println(part2(input))   // 213089
}

fun elfIndexOrNull(calories: String): Int? = calories.ifEmpty {
    ++elfIndex
    null
}?.let { elfIndex }


fun groupInt() = 0