package day06

import readInput

/**
 * Answers from [Advent of Code 2022 Day 6 | Kotlin](https://youtu.be/VbBhaQhW0zk)
 */
fun main() {
    fun CharSequence.allUnique() = toSet().count() == length

    fun solve(input: String, windowsSize: Int) =
        input.windowedSequence(windowsSize).indexOfFirst { it.allUnique() } + windowsSize

    fun solveNatively(input: String, windowsSize: Int): Int {
        val windowed = input.windowed(windowsSize)
        for ((index, window) in windowed.withIndex()) {
            if (window.toSet().count() == windowsSize) {
                return index + windowsSize
            }
        }
        return -1
    }

    val input = readInput("day06")

    println(solve(input[0], 4))     // 1034
    println(solve(input[0], 14))    // 2472
}