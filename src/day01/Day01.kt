package day01

import groupBySkipNullKey
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var elfIndex = 0
        val results = input.groupBySkipNullKey({ calories ->
            calories.ifEmpty {
                ++elfIndex
                null
            }?.let { elfIndex }
        }, String::toInt, { 0 }) { group, value ->
            group + value
        }
        return results.values.maxOrNull()!!
    }

    fun part2(input: List<String>): Int {
        var elfIndex = 0
        val results = input.groupBySkipNullKey({ calories ->
            calories.ifEmpty {
                ++elfIndex
                null
            }?.let { elfIndex }
        }, String::toInt, { 0 }) { group, value ->
            group + value
        }.values.sortedDescending()
        return results.take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("day01")
    println(part1(input))
    println(part2(input))
}
