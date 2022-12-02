package day01

import readString
import java.util.*

/**
 * Answer basically from [Advent of Code 2022 Day 1 | Kotlin](https://youtu.be/ntbsbqLCKDs)
 */
fun main() {
    fun part1(input: String): Int {
        val data = parseInput(input)
        return data.topNElvesLogN(1)
    }

    fun part2(input: String): Int {
        val data = parseInput(input)
        return data.topNElvesLogN(3)
    }

    val testInput = readString("day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readString("day01")
    println(part1(input))   // 72718
    println(part2(input))   // 213089
}

fun parseInput(input: String) =
    input.split("\n\n")
        .map { elf ->
            elf.lines()
                .map(String::toInt)
                .sum()
        }

/**
 * normal method, O(NlogN) time complexity
 */
fun List<Int>.topNElves(n: Int): Int =
    sortedDescending().take(n).sum()

/**
 * O(size * log size) -> O(size * log n)
 */
fun List<Int>.topNElvesNLogN(n: Int): Int {
    val best = PriorityQueue<Int>()     // TreeSet can't accept same value and will lose it, use PriorityQueue here
    forEach { calories ->
        best.add(calories)
        if (best.size > n) {
            best.poll()
        }
    }
    return best.sum()
}

/**
 * O(size * log n) -> O(log n)
 */
fun List<Int>.topNElvesLogN(n: Int): Int {
    fun findTopN(n: Int, element: List<Int>): List<Int> {
        if (this.size == n) return element
        val x = element.random()
        val small = element.filter { it < x }
        val equal = element.filter { it == x }
        val big = element.filter { it > x }
        if (big.size >= n) return findTopN(n, element)
        if (equal.size + big.size >= n) return (equal + big).takeLast(n)
        return findTopN(n - equal.size - big.size, small) + equal + big
    }
    return findTopN(n, this).sum()
}