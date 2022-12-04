package day03

import readInput

/**
 * Answers from [Advent of Code 2022 Day 3 | Kotlin](https://youtu.be/IPLfo4zXNjk)
 */
fun main() {
    fun part1(input: List<String>): Int {
        return input.map { rucksack ->
            rucksack.substring(0 until rucksack.length / 2) to rucksack.substring(rucksack.length / 2)
        }.flatMap { (first, second) ->
            first.toSet() intersect second.toSet()
        }.sumOf {
            it.toScore()
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map { elfGroup ->
                elfGroup.zipWithNext()
                    .map { (first, second) ->
                        first.toSet() intersect second.toSet()
                    }
            }.flatMap { sharedItems ->
                // index safe because we chunked by 3 and then did zipWithNext
                sharedItems[0] intersect sharedItems[1]
            }.sumOf {
                it.toScore()
            }
    }

    val testInput = readInput("day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day03")
    println(part1(input))   // 8123
    println(part2(input))   // 2620
}

// In the test example, the priority of the item type that appears in both compartments
// of each rucksack is 16 (p), 38 (L), 42 (P), 22 (v), 20 (t), and 19 (s); the sum of these is 157.
private fun Char.toScore(): Int =
    if (isUpperCase()) {
        this - 'A' + 27
    } else {
        this - 'a' + 1
    }