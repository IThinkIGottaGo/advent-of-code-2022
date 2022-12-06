package day04

import readInput

/**
 * Answers from [Advent of Code 2022 Day 4 | Kotlin](https://youtu.be/dBIbr55YS0A)
 */
fun main() {
    val testInput = Day04(readInput("day04_test"))
    check(testInput.solvePart1() == 2)
    check(testInput.solvePart2() == 4)

    val input = Day04(readInput("day04"))
    println(input.solvePart1())   // 580
    println(input.solvePart2())   // 895
}

class Day04(input: List<String>) {
    private val ranges: List<Pair<IntRange, IntRange>> = input.map { it.asRanges() }

    fun solvePart1(): Int =
        ranges.count { it.first fullyOverlaps it.second || it.second fullyOverlaps it.first }

    fun solvePart2(): Int =
        ranges.count { it.first overlaps it.second }

    private infix fun IntRange.fullyOverlaps(other: IntRange): Boolean =
        first <= other.first && last >= other.last

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        first <= other.last && other.first <= last

    private fun String.asRanges(): Pair<IntRange, IntRange> =
        substringBefore(",").asIntRange() to substringAfter(",").asIntRange()

    private fun String.asIntRange(): IntRange =
        substringBefore("-").toInt()..substringAfter("-").toInt()
}