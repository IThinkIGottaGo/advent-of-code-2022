package day06

import readString

/**
 * Answers from [Advent of Code 2022 Day 6 | Kotlin](https://youtu.be/VbBhaQhW0zk)
 */
val input = readString("day06")

// Solution sent in by Alexander of Tralle
// Further optimization: IntArray(?)
// time complexity：O(n)

class Day6 {
    private fun String.startMessageIndex(uniqueLength: Int): Int {
        val duplicateIndexMap = mutableMapOf<Char, Int>()
        var mostRecentDuplicateIndex = 0
        var index = 0
        return indexOfFirst { char ->
            val lastSeenIndex = duplicateIndexMap.put(char, index) ?: 0
            mostRecentDuplicateIndex = mostRecentDuplicateIndex.coerceAtLeast(lastSeenIndex)
            index++ - mostRecentDuplicateIndex >= uniqueLength
        } + 1
    }

    fun part1(input: String): Int = input.startMessageIndex(4)
    fun part2(input: String): Int = input.startMessageIndex(14)
}

fun main() {
    val d = Day6()

    println(d.part1(input))    // 1034
    println(d.part2(input))    // 2472
}