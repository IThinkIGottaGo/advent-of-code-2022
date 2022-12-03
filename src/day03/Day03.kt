package day03

import readInput

fun main() {
    val priority = buildMap {
        ('a'..'z').forEachIndexed { i, c -> put(c, i + 1) }
        ('A'..'Z').forEachIndexed { i, c -> put(c, i + 27) }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val c1 = line.substring(0, line.length / 2)
            val c2 = line.substring(line.length / 2, line.length)
            val commonItems = c1.asIterable().intersect(c2.asIterable().toSet())
            commonItems.sumOf { priority[it]!! }
        }
    }

    fun part2(input: List<String>): Int {
        return input.windowed(3, 3) { (g1, g2, g3) ->
            val commonBadge =
                g1.asIterable().intersect(g2.asIterable().toSet()).intersect(g3.asIterable().toSet()).first()
            priority[commonBadge]!!
        }.sum()
    }

    val testInput = readInput("day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day03")
    println(part1(input))   // 8123
    println(part2(input))   // 2620
}