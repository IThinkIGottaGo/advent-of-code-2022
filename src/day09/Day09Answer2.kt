package day09

import day09.Directions.*
import readInput
import kotlin.math.abs

/**
 * Answers from [Advent of Code 2022 Day 9 | Kotlin](https://youtu.be/ShU9dNUa_3g)
 */
enum class Directions {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

data class Movement(val direction: Directions) {
    fun move(v: Vec2): Vec2 {
        return when (direction) {
            UP -> Vec2(v.x, v.y + 1)
            DOWN -> Vec2(v.x, v.y - 1)
            LEFT -> Vec2(v.x - 1, v.y)
            RIGHT -> Vec2(v.x + 1, v.y)
        }
    }
}

fun String.toMovements(): List<Movement> {
    val (dir, len) = split(" ")
    val direction = when (dir) {
        "U" -> UP
        "D" -> DOWN
        "L" -> LEFT
        "R" -> RIGHT
        else -> error("what")
    }
    return List(len.toInt()) { Movement(direction) }
}

val input = readInput("day09")
val instructions = input.flatMap { it.toMovements() }

fun main() {
    part1()
    part2()
}

fun part1() {
    val visited = mutableSetOf<Vec2>()
    var head = Vec2(0, 0)
    var tail = Vec2(0, 0)
    visited += tail
    for (instruction in instructions) {
        head = instruction.move(head)
        if (head adjacentTo tail) continue    // or even continue the outer loop!
        val offset = head - tail
        val normalizedOffset = Vec2(
            offset.x.coerceIn(-1..1),
            offset.y.coerceIn(-1..1)
        )
        tail += normalizedOffset
        visited += tail
    }
    println(visited.size)    // 5981
}

fun part2() {
    val visited = hashSetOf<Vec2>()
    val snake = MutableList(10) { Vec2(0, 0) }
    visited += Vec2(0, 0)
    for (instruction in instructions) {
        val head = snake[0]
        snake[0] = instruction.move(head)

        for (headIdx in 0 until 9) {
            val curTail = snake[headIdx + 1]
            val curHead = snake[headIdx]
            if (curTail adjacentTo curHead) continue    // or even continue the outer loop!
            val offset = curHead - curTail
            val normalizedOffset = Vec2(
                offset.x.coerceIn(-1..1),
                offset.y.coerceIn(-1..1)
            )
            val newTail = curTail + normalizedOffset
            snake[headIdx + 1] = newTail
        }

        visited += snake[snake.lastIndex]
    }
    println(visited.size)    // 2352
}

data class Vec2(val x: Int, val y: Int) {
    infix fun adjacentTo(other: Vec2): Boolean {
        return abs(x - other.x) <= 1 && abs(y - other.y) <= 1
    }

    operator fun minus(other: Vec2): Vec2 {
        return Vec2(this.x - other.x, this.y - other.y)
    }

    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }
}