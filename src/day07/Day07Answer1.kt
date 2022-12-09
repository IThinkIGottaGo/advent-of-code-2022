package day07

import readInput

/**
 * Answers from [Advent of Code 2022 Day 7 | Kotlin](https://youtu.be/Q819VW8yxFo)
 */
fun main() {
    val day7 = Day7(readInput("day07"))

    println(day7.part1())   // 1723892
    println(day7.part2())   // 8474158
}

private val PATTERN = """[$] cd (.*)|(\d+).*""".toRegex()

class Day7(lines: List<String>) {
    private val sizes = buildMap {
        put("", 0)
        var cwd = ""
        for (line in lines) {
            val match = PATTERN.matchEntire(line) ?: continue
            match.groups[1]?.value?.let { dir ->
                cwd = when (dir) {
                    "/" -> ""
                    ".." -> cwd.substringBeforeLast('/', "")
                    else -> if (cwd.isEmpty()) dir else "$cwd/$dir"
                }
            } ?: match.groups[2]?.value?.toIntOrNull()?.let { size ->
                var dir = cwd
                while (true) {
                    put(dir, getOrElse(dir) { 0 } + size)
                    if (dir.isEmpty()) break
                    dir = dir.substringBeforeLast('/', "")
                }
            }
        }
    }

    fun part1(): Int = sizes.values.sumOf { if (it <= 100000) it else 0 }

    fun part2(): Int {
        val total = sizes.getValue("")
        return sizes.values.asSequence().filter { 70000000 - (total - it) >= 30000000 }.min()
    }
}