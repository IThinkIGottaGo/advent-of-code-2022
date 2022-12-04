package day04

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val lambda = { line: String ->
            val (range1, range2) = parseLine(line)
            val unionSection = range1 union range2
            if (range1 == unionSection || range2 == unionSection) 1 else 0
        }
        return input.sumOf(lambda)
    }

    fun part2(input: List<String>): Int {
        val lambda = { line: String ->
            val (range1, range2) = parseLine(line)
            if ((range1 intersect range2).isNotEmpty()) 1 else 0
        }
        return input.sumOf(lambda)
    }

    val testInput = readInput("day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day04")
    println(part1(input))   // 580
    println(part2(input))   // 895
}

fun parseLine(line: String): Pair<Set<Int>, Set<Int>> {
    val (a1, a2, b1, b2) = line.split('-', ',').map(String::toInt)
    val range1 = (a1..a2).toSet()
    val range2 = (b1..b2).toSet()
    return range1 to range2
}