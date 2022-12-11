package day10

import addOrSet
import getOrAdd
import readInput

fun main() {
    val testInput = readInput("day10_test")
    check(resolve(testInput, true, 20, 60, 100, 140, 180, 220) == 13140)
    println()

    val input = readInput("day10")
    println(resolve(input, true, 20, 60, 100, 140, 180, 220))   // FBURHZCH
}

fun resolve(input: List<String>, printMode: Boolean, vararg cycles: Int): Int {
    var register = 1
    var currentCycles = 0
    var remainCycles: Int
    var result = 0
    val drawPoints = mutableListOf<String>()
    input.forEach { line ->
        val (inst, args) = line.split(" ").run { Pair(this[0], this.getOrNull(1)?.toInt() ?: 0) }
        remainCycles = getRemainCycles(inst)
        while (remainCycles != 0) {
            ++currentCycles
            printPreProcess(currentCycles, register, drawPoints)

            // register process part
            --remainCycles
            if (currentCycles in cycles) {
                result += register * currentCycles
            }
            if (remainCycles == 0 && inst == "addx") register += args
        }
    }
    doPrint(printMode, drawPoints)
    return result
}

private fun getRemainCycles(inst: String) =
    when (inst) {
        "addx" -> 2
        "noop" -> 1
        else -> error("unknown instructions.")
    }

private fun printPreProcess(currentCycles: Int, register: Int, drawPoints: MutableList<String>) {
    val drawPosition = currentCycles - 1
    if ((drawPosition % 40) in (register - 1..register + 1)) {
        drawPoints.addOrSet(drawPosition, "#")
    } else {
        drawPoints.getOrAdd(drawPosition) { "." }
    }
}

private fun doPrint(mode: Boolean, drawPoints: List<String>) {
    if (mode) {
        drawPoints.forEachIndexed { i, s ->
            print(s)
            if ((i + 1) % 40 == 0) println()
        }
    }
}