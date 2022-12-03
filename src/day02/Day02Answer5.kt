package day02

import readInput

/**
 * Answers from [Advent of Code 2022 - Day 2, in Kotlin - Rock Paper Scissors](https://todd.ginsberg.com/post/advent-of-code/2022/day2/)
 */
fun main() {
    fun part1(input: List<String>): Int {
        return Day02(input).solvePart1()
    }

    fun part2(input: List<String>): Int {
        return Day02(input).solvePart2()
    }

    val testInput = readInput("day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02")
    println(part1(input))   // 12586
    println(part2(input))   // 13193
}

class Day02(private val input: List<String>) {
    private val part1Scores: Map<String, Int> =
        mapOf(
            "A X" to 1 + 3,
            "A Y" to 2 + 6,
            "A Z" to 3 + 0,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 1 + 6,
            "C Y" to 2 + 0,
            "C Z" to 3 + 3,
        )

    private val part2Scores: Map<String, Int> =
        mapOf(
            "A X" to 3 + 0,
            "A Y" to 1 + 3,
            "A Z" to 2 + 6,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 2 + 0,
            "C Y" to 3 + 3,
            "C Z" to 1 + 6,
        )

    fun solvePart1(): Int =
        input.sumOf { part1Scores[it] ?: 0 }

    fun solvePart2(): Int =
        input.sumOf { part2Scores[it] ?: 0 }
}