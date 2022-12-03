package day02

import readInput

/**
 * Answers from [Advent of Code 2022 Day 2 | Kotlin](https://youtu.be/Fn0SY2yGDSA)
 */
fun main() {
    fun part1(input: List<String>): Int {
        fun shapeScore(shape: Char) = (shape - 'X' + 1)

        fun resultScore(line: String): Int {
            return when (line) {
                "B X", "C Y", "A Z" -> 0
                "A X", "B Y", "C Z" -> 3
                "C X", "A Y", "B Z" -> 6
                else -> error("Check your inputs")
            }
        }

        return input.sumOf { round ->
            shapeScore(shape = round[2]) + resultScore(round)
        }
    }

    fun part2(input: List<String>): Int {
        fun shapeScore(line: String): Int {
            return when (line) {
                "A Y", "B X", "C Z" -> 1     // Rock
                "B Y", "C X", "A Z" -> 2     // Paper
                "C Y", "A X", "B Z" -> 3     // Scissors
                else -> error("Check your inputs")
            }
        }

        fun resultScore(result: Char) = (result - 'X') * 3

        return input.sumOf { round ->
            shapeScore(round) + resultScore(round[2])
        }
    }

    val testInput = readInput("day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02")
    println(part1(input))   // 12586
    println(part2(input))   // 13193
}