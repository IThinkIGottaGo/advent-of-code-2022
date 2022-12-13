package day10

import readInput

/**
 * Answers from [Advent of Code 2022 Day 10 | Kotlin](https://youtu.be/KVyeNmFHoL4)
 */
class Day10 {
    private var input = readInput("day10")

    fun part1b(): Int {
        var x = 1
        var currentCycle = 1
        var signalStrength = 0

        fun increaseSignalStrength() {
            if (currentCycle % 40 == 20) {
                signalStrength += (currentCycle * x)
            }
        }

        input.forEach { instruction ->
            currentCycle++
            increaseSignalStrength()

            if (instruction != "noop") {
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
                increaseSignalStrength()
            }
        }
        return signalStrength
    }

    // Part 2 - Option 1
    private fun printCRTValue(index: Int, x: Int) {
        val adjustedIndex = index % 40

        if (adjustedIndex == 0) {
            println()
        }

        if (adjustedIndex in x - 1..x + 1) {
            print("#")
        } else {
            print(".")
        }
    }

    fun part2() {
        var x = 1
        var currentCycle = 1

        input.forEach { instruction ->
            printCRTValue(currentCycle - 1, x)
            currentCycle++

            if (instruction != "noop") {
                printCRTValue(currentCycle - 1, x)
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
            }
        }
    }

    // Part 2 - Option 2
    fun part2bLists(): Array<CharArray> {
        var x = 1
        var currentCycle = 1
        val screen = Array(6) { CharArray(40) { '.' } }

        fun drawPixel() {
            val horizontalPos = (currentCycle - 1) % 40
            if (horizontalPos in x - 1..x + 1) {
                screen[(currentCycle - 1) / 40][horizontalPos] = '#'
            }
        }

        input.forEach { instruction ->
            drawPixel()
            currentCycle++

            if (instruction != "noop") {
                drawPixel()
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
            }
        }
        return screen
    }
}

fun main() {
    println(Day10().part1b())   // 13720
    Day10().part2()             // FBURHZCH
}