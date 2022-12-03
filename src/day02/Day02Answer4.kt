package day02

import day02.Gesture.*
import day02.Outcome.*
import readInput

/**
 * Answers from [Advent of Code 2022 Day 2 | Kotlin](https://youtu.be/Fn0SY2yGDSA)
 */
fun main() {
    println(part1())   // 12586
    println(part2())   // 13193
}

enum class Gesture(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

fun Gesture.beats(): Gesture {
    return when (this) {
        ROCK -> SCISSORS
        PAPER -> ROCK
        SCISSORS -> PAPER
    }
}

fun Char.toGesture(): Gesture {
    return when (this) {
        'A', 'X' -> ROCK
        'B', 'Y' -> PAPER
        'C', 'Z' -> SCISSORS
        else -> error("Unknown input $this")
    }
}

val input = readInput("day02")
    .map {
        val (a, b) = it.split(" ")
        a.first() to b.first()
    }

fun part1(): Int {
    return input.sumOf { (opponent, you) ->
        calculateScore(opponent.toGesture(), you.toGesture())
    }
}

fun part2(): Int {
    return input.sumOf { (opponent, you) ->
        val yourHand = handForDesiredOutcome(opponent.toGesture(), you.toOutcome())
        calculateScore(opponent.toGesture(), yourHand)
    }
}


enum class Outcome(val points: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6)
}

// Calculate the outcome from the perspective of `first`
fun calculateOutcome(first: Gesture, second: Gesture): Outcome =
    when {
        first == second -> DRAW
        first.beats() == second -> WIN
        else -> LOSS
    }


fun calculateScore(opponent: Gesture, you: Gesture): Int {
    val outcome = calculateOutcome(you, opponent)
    return you.points + outcome.points
}

// region part2

fun Char.toOutcome(): Outcome {
    return when (this) {
        'X' -> LOSS
        'Y' -> DRAW
        'Z' -> WIN
        else -> error("Unknown input $this")
    }
}

fun handForDesiredOutcome(opponentGesture: Gesture, desiredOutcome: Outcome): Gesture {
    return when (desiredOutcome) {
        DRAW -> opponentGesture
        LOSS -> opponentGesture.beats()
        WIN -> opponentGesture.beatenBy()
    }
}

fun Gesture.beatenBy(): Gesture {
    return when (this) {
        SCISSORS -> ROCK
        ROCK -> PAPER
        PAPER -> SCISSORS
    }
}

// endregion