package day10

import readInput

/**
 * Answers from [Advent of Code 2022 Day 10 | Kotlin](https://youtu.be/KVyeNmFHoL4)
 */
val input = readInput("day10")

sealed class Instruction

class Noop : Instruction()

class Addx(val num: Int) : Instruction()

fun String.parse(): Instruction {
    if (startsWith("noop")) {
        return Noop()
    } else {
        val (_, foo) = split(" ")
        return Addx(foo.toInt())
    }
}

object Part1 {
    val signals = mutableListOf<Int>()
    var x = 1
    var cycle = 0

    fun signal() {
        cycle++
        if (cycle % 40 == 20) {
            signals += cycle * x
        }
    }

    fun execute(insts: List<Instruction>) {
        for (i in insts) {
            when (i) {
                is Addx -> {
                    signal()
                    signal()
                    x += i.num
                }

                is Noop -> {
                    signal()
                }
            }
        }
        println(signals.sum())
    }
}

object Part2 {
    var x = 1
    var crt = 0

    fun crt() {
        val sprite = (x - 1..x + 1)
        if (crt > 0 && crt % 40 == 0) {
            println()
        }
        if (crt % 40 in sprite) {
            print("#")
        } else print('.')

        crt++
    }

    fun execute(insts: List<Instruction>) {
        for (i in insts) {
            when (i) {
                is Addx -> {
                    // begin executing addx
                    crt()   // crt draws
                    crt()   // crt draws
                    x += i.num  // finish executing, CRT now something else
                }

                is Noop -> {
                    // begin executing noop
                    crt()   // crt draws
                }
            }
        }
    }
}

fun main() {
    val insts = input.map { it.parse() }
    Part1.execute(insts)    // 13720
    Part2.execute(insts)    // FBURHZCH
}