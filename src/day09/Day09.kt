package day09

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val head = Node()
        val tail = Node()
        head.next = tail
        doMove(head, input)
        return tail.visited.size
    }

    fun part2(input: List<String>): Int {
        val head = Node()
        var tail = head
        repeat(9) {
            tail.next = Node()
            tail = tail.next!!
        }
        doMove(head, input)
        return tail.visited.size
    }

    val testInput = readInput("day09_test")
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)

    val input = readInput("day09")
    println(part1(input))   // 5981
    println(part2(input))   // 2352
}

fun doMove(head: Node, input: List<String>) {
    input.forEach { line ->
        val (action, steps) = line.split(" ").let { Pair(it[0], it[1].toInt()) }
        repeat(steps) {
            head.move(action)   // each move 1 step, repeat steps times
        }
    }
}

data class Node(var x: Int = 0, var y: Int = 0) {
    var next: Node? = null
    val visited = hashSetOf(Pair(x, y))

    fun move(action: String) {
        when (action) {
            "U" -> ++y
            "D" -> --y
            "L" -> --x
            "R" -> ++x
            else -> error("unknown direction!")
        }
        next?.sync(this)
    }

    private fun sync(prev: Node) {
        val (ofx, ofy) = calcOffset(prev)
        // Any state that requires Tail sync, it has an offset greater than 2 in at least one direction.
        // Then shrink distance to that direction.
        if (abs(ofx) >= 2 || abs(ofy) >= 2) {
            if (ofx > 0) ++x else if (ofx < 0) --x
            if (ofy > 0) ++y else if (ofy < 0) --y
            visited += Pair(x, y)
        }
        next?.sync(this)
    }

    private fun calcOffset(prev: Node): Pair<Int, Int> = Pair(prev.x - x, prev.y - y)
}