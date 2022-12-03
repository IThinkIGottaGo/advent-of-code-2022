package day02

import readInput

/**
 * Answers from [Advent of Code 2022 Day 2 | Kotlin](https://youtu.be/Fn0SY2yGDSA)
 */
fun main() {
    operator fun String.component1() = this[0]
    operator fun String.component2() = this[1]
    operator fun String.component3() = this[2]

    fun part1(input: List<String>): Int {
        // X Y Z
        // 0 1 2
        // 1 2 3
        fun shapeScore(shape: Char) = (shape - 'X' + 1)

        fun resultScore(theirShape: Char, myShape: Char): Int {
            return when (theirShape to myShape) {
                'B' to 'X', 'C' to 'Y', 'A' to 'Z' -> 0
                'A' to 'X', 'B' to 'Y', 'C' to 'Z' -> 3
                'C' to 'X', 'A' to 'Y', 'B' to 'Z' -> 6
                else -> error("Check your inputs")
            }
        }

        return input.sumOf { round ->
            val (theirShape, _, myShape) = round
            shapeScore(myShape) + resultScore(theirShape, myShape)
        }
    }

    fun part2(input: List<String>): Int {
        fun shapeScore(theirShape: Char, desiredResult: Char): Int {
            return when (theirShape to desiredResult) {
                'A' to 'Y', 'B' to 'X', 'C' to 'Z' -> 1     // Rock
                'B' to 'Y', 'C' to 'X', 'A' to 'Z' -> 2     // Paper
                'C' to 'Y', 'A' to 'X', 'B' to 'Z' -> 3     // Scissors
                else -> error("Check your inputs")
            }
        }

        // X Y Z
        // 0 1 2
        // 0 3 6
        fun resultScore(result: Char) = (result - 'X') * 3

        return input.sumOf { round ->
            val (theirShape, _, result) = round
            shapeScore(theirShape, result) + resultScore(result)
        }
    }

    val testInput = readInput("day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02")
    println(part1(input))   // 12586
    println(part2(input))   // 13193
}