package day02

import readInput

/**
 * Answers from [Advent of Code 2022 Day 2 | Kotlin](https://youtu.be/Fn0SY2yGDSA)
 */
fun main() {
    fun score(myShape: Shape1, theirShape: Shape1): Int {
        return myShape.score + when {
            theirShape beats myShape -> 0
            theirShape beatenBy myShape -> 6
            else -> 3
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { round ->
            val theirShape = Shape1(round[0])
            val myShape = Shape1(round[2])
            score(myShape, theirShape)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { round ->
            val theirShape = Shape1(round[0])

            // X - lose
            // Y - tie
            // Z - win
            val myShape = when (round[2]) {
                'X' -> theirShape.prev()
                'Z' -> theirShape.next()
                else -> theirShape
            }
            score(myShape, theirShape)
        }
    }

    val testInput = readInput("day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02")
    println(part1(input))   // 12586
    println(part2(input))   // 13193
}

data class Shape1 internal constructor(val score: Int) {
    fun next() = Shape1((score % 3) + 1)

    fun prev() = if (score == 1) Shape1(3) else Shape1(score - 1)

    // make shapeA >\<\= shapeB possible, but only work for this case, if try to sort List<Shape1> would be break.
    operator fun compareTo(other: Shape1): Int {
        return when (other) {
            this -> 0
            this.next() -> -1
            else -> 1
        }
    }
}

fun Shape1(char: Char): Shape1 {
    require(char in 'A'..'C' || char in 'X'..'Z')
    return Shape1(
        when (char) {
            'A', 'X' -> 1   // Rock
            'B', 'Y' -> 2   // Paper
            else -> 3       // Scissors
        }
    )
}

infix fun Shape1.beats(other: Shape1) = this > other

infix fun Shape1.beatenBy(other: Shape1) = this < other