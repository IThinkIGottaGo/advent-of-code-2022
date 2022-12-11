package day09

import readInput
import java.lang.Math.abs

/**
 * Answers from [Advent of Code 2022 Day 9 | Kotlin](https://youtu.be/ShU9dNUa_3g)
 */
data class Move(val dx: Int, val dy: Int)

enum class Direction(val move: Move) {
    R(Move(1, 0)),
    L(Move(-1, 0)),
    U(Move(0, 1)),
    D(Move(0, -1)),
}

data class Pos(val x: Int, val y: Int) {
    override fun toString(): String = "(x:$x, y:$y)"
}

operator fun Pos.plus(move: Move): Pos = copy(x + move.dx, y + move.dy)

operator fun Pos.minus(other: Pos): Move = Move(x - other.x, y - other.y)

// Chebyshev's Chessboard distance
val Move.distance: Int get() = maxOf(abs(dx), abs(dy))

fun tailToHeadAttraction(head: Pos, tail: Pos): Move {
    val tailToHead = head - tail
    return if (tailToHead.distance > 1) {
        Move(tailToHead.dx.coerceIn(-1, 1), tailToHead.dy.coerceIn(-1, 1))
    } else {
        Move(0, 0)
    }
}

fun main() {
    val input = readInput("day09").map { line -> line.split(" ").let { (d, n) -> Direction.valueOf(d) to n.toInt() } }

    run part1@{
        var head = Pos(0, 0)
        var tail = head
        val tailVisited = mutableSetOf(tail)

        for ((d, n) in input) {
            repeat(n) {
                head += d.move
                tail += tailToHeadAttraction(head, tail)
                tailVisited += tail
            }
        }
        println(tailVisited.size)   // 5981
    }

    run part2@{
        val knotsNumber = 10
        val knots = MutableList(knotsNumber) { Pos(0, 0) }
        val tailVisited = mutableSetOf(knots.last())

        for ((d, n) in input) {
            repeat(n) {
                knots[0] = knots[0] + d.move
                for ((headIndex, tailIndex) in knots.indices.zipWithNext()) {
                    knots[tailIndex] = knots[tailIndex] + tailToHeadAttraction(knots[headIndex], knots[tailIndex])
                }
                tailVisited += knots.last()
            }
        }
        println(tailVisited.size)   // 2352
    }
}