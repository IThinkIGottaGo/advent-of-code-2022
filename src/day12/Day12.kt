package day12

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val settled = mutableListOf<Node>()
        val unsettled = mutableListOf<Node>()
        val grid = parseInput(input)
        val startNode = Node(grid.getSpecifyPositionAndReplaceChar('S'), 0)     // replace start position 'S' to 'a'
        val endPos = grid.getSpecifyPositionAndReplaceChar('E')               // replace end position 'E' to 'z'
        unsettled += startNode

        while (unsettled.isNotEmpty()) {
            val node = unsettled.removeShortestNode()
            settled += node
            val waitSync = grid.nextPossiblePositions(settled, node.position) { it <= 1 }
            addOrSyncSteps(unsettled, waitSync, node)
        }
        return settled.first { it.position == endPos }.steps
    }

    fun part2(input: List<String>): Int {
        val settled = mutableListOf<Node>()
        val unsettled = mutableListOf<Node>()
        val grid = parseInput(input)
        val aPosInGrid = grid.getAPosInGrid()
        val startNode = Node(grid.getSpecifyPositionAndReplaceChar('E'), 0)
        grid.getSpecifyPositionAndReplaceChar('S')
        unsettled += startNode

        while (unsettled.isNotEmpty()) {
            val node = unsettled.removeShortestNode()
            settled += node
            val waitSync = grid.nextPossiblePositions(settled, node.position) { it >= -1 }
            addOrSyncSteps(unsettled, waitSync, node)
        }
        return settled.filter { it.position in aPosInGrid }.minBy { it.steps }.steps
    }

    val testInput = readInput("day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("day12")
    println(part1(input))   // 497
    println(part2(input))   // 492
}

typealias Grid = List<MutableList<Char>>

data class Position(val i: Int, val j: Int)

data class Node(val position: Position, var steps: Int) {
    override fun toString(): String {
        return "(${position.i}, ${position.j}, $steps)"
    }
}

fun parseInput(input: List<String>): Grid = input.map(String::toMutableList)

fun Grid.getSpecifyPositionAndReplaceChar(c: Char): Position {
    for (i in indices) {
        for (j in this[0].indices) {
            if (this[i][j] == c) {
                if (c == 'S') this[i][j] = 'a'
                else if (c == 'E') this[i][j] = 'z'
                return Position(i, j)
            }
        }
    }
    error("can not find $c position.")
}

fun Grid.nextPossiblePositions(
    settled: MutableList<Node>,
    position: Position,
    condition: (Int) -> Boolean
): MutableList<Position> {
    val (i, j) = position
    val possiblePos = mutableListOf<Position>()
    tryMoveUp(settled, i, j, condition)?.also { possiblePos += it }
    tryMoveDown(settled, i, j, condition)?.also { possiblePos += it }
    tryMoveLeft(settled, i, j, condition)?.also { possiblePos += it }
    tryMoveRight(settled, i, j, condition)?.also { possiblePos += it }
    return possiblePos
}

fun Grid.tryMoveUp(settled: MutableList<Node>, i: Int, j: Int, elevationCondition: (Int) -> Boolean): Position? {
    val p = Position(i - 1, j)
    if (i - 1 >= 0 && elevationCondition(this[i - 1][j] - this[i][j]) && p !in settled) {
        return p
    }
    return null
}

fun Grid.tryMoveDown(settled: MutableList<Node>, i: Int, j: Int, elevationCondition: (Int) -> Boolean): Position? {
    val p = Position(i + 1, j)
    if (i + 1 < this.size && elevationCondition(this[i + 1][j] - this[i][j]) && p !in settled) {
        return p
    }
    return null
}

fun Grid.tryMoveLeft(settled: MutableList<Node>, i: Int, j: Int, elevationCondition: (Int) -> Boolean): Position? {
    val p = Position(i, j - 1)
    if (j - 1 >= 0 && elevationCondition(this[i][j - 1] - this[i][j]) && p !in settled) {
        return p
    }
    return null
}

fun Grid.tryMoveRight(settled: MutableList<Node>, i: Int, j: Int, elevationCondition: (Int) -> Boolean): Position? {
    val p = Position(i, j + 1)
    if (j + 1 < this[0].size && elevationCondition(this[i][j + 1] - this[i][j]) && p !in settled) {
        return p
    }
    return null
}

fun MutableList<Node>.removeShortestNode(): Node {
    return minBy { it.steps }.also { remove(it) }
}

fun addOrSyncSteps(unsettled: MutableList<Node>, waitSyncQueued: MutableList<Position>, node: Node) {
    waitSyncQueued.forEach { pos ->
        if (pos !in unsettled) unsettled += Node(pos, node.steps + 1)
        else {
            val oldNode = unsettled.first { it.position == pos }
            if (node.steps + 1 < oldNode.steps) {
                oldNode.steps = node.steps + 1
            }
        }
    }
}

fun Grid.getAPosInGrid(): List<Position> {
    val list = mutableListOf<Position>()
    for (i in indices) {
        for (j in this[0].indices) {
            if (this[i][j] == 'a') {
                list += Position(i, j)
            }
        }
    }
    return list
}

operator fun MutableList<Node>.contains(position: Position): Boolean {
    return position in this.map { it.position }
}