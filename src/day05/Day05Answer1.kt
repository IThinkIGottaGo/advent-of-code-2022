package day05

import readInput

/**
 * Answers from [Advent of Code 2022 Day 5 | Kotlin](https://youtu.be/lKq6r5Nt8Yo)
 */
fun main() {
    val lines = readInput("day05")
    val day05 = Day05()

    // Calculate number of stacks needed
    val numberOfStacks = day05.numberOfStacks(lines)
    val stacks = List(numberOfStacks) { ArrayDeque<Char>() }

    // Fill the stacks
    day05.populateStacks(lines) { stackNumber, value ->
        stacks[stackNumber].addLast(value)
    }

    // Get the  moves
    val moves = lines.filter { it.contains("move") }.map { Move.of(it) }

    // Perform the moves
    println(day05.partOne(stacks.map { ArrayDeque(it) }, moves))   // BZLVHBWQF
    println(day05.partTwo(stacks, moves))                          // TDGJQTZSL
}

class Day05 {
    fun numberOfStacks(lines: List<String>): Int {
        return lines
            .dropWhile { it.contains("[") }
            .first()
            .split(" ")
            .filter { it.isNotBlank() }
            .maxOf { it.toInt() }
    }

    fun partOne(stacks: List<ArrayDeque<Char>>, moves: List<Move>): String {
        moves.forEach { step -> repeat(step.quantity) { stacks[step.target].addFirst(stacks[step.source].removeFirst()) } }
        return stacks.map { it.first() }.joinToString(separator = "")
    }

    fun partTwo(stacks: List<ArrayDeque<Char>>, moves: List<Move>): String {
        moves.map { step ->
            stacks[step.source]
                .subList(0, step.quantity)
                .asReversed()
                .onEach { stacks[step.target].addFirst(it) }
                .onEach { stacks[step.source].removeFirst() }
        }
        return stacks.map { it.first() }.joinToString(separator = "")
    }

    fun populateStacks(lines: List<String>, onCharacterFound: (Int, Char) -> Unit) {
        lines
            .filter { it.contains("[") }
            .map { line ->
                line.mapIndexed { index, char ->
                    if (char.isLetter()) {
                        val stackNumber = index / 4
                        val value = line[index]
                        onCharacterFound(stackNumber, value)
                    }
                }
            }
    }
}

data class Move(val quantity: Int, val source: Int, val target: Int) {
    companion object {
        fun of(line: String): Move {
            return line
                .split(" ")
                .filterIndexed { index, _ -> index % 2 == 1 }
                .map { it.toInt() }
                .let { Move(it[0], it[1] - 1, it[2] - 1) }
        }
    }
}