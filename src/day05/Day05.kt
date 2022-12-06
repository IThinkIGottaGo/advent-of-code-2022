package day05

import getOrAdd
import readString

fun main() {
    fun part1(input: String): String {
        val (stackString, procedureString) = input.split("\n\n")
        val stacks = processStackString(stackString)
        val procedures = buildProcedures(procedureString)
        CrateMover9000(procedures).process(stacks)
        return concatStacksTopCrate(stacks)
    }

    fun part2(input: String): String {
        val (stackString, procedureString) = input.split("\n\n")
        val stacks = processStackString(stackString)
        val procedures = buildProcedures(procedureString)
        CrateMover9001(procedures).process(stacks)
        return concatStacksTopCrate(stacks)
    }

    val testInput = readString("day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readString("day05")
    println(part1(input))   // BZLVHBWQF
    println(part2(input))   // TDGJQTZSL
}

fun processStackString(stackString: String): MutableList<ArrayDeque<Char>> {
    val stackList = mutableListOf<ArrayDeque<Char>>()
    val indexPart = stackString.lines().last()
    val stackPart = stackString.lines().dropLast(1)
    // this can not handle stack size >9 case
    indexPart.forEachIndexed { i, c ->
        if (c.isDigit()) {
            stackPart.forEach { crates ->
                if (crates.getOrElse(i) { '0' }.isLetter()) {
                    val stack = stackList.getOrAdd(c.digitToInt() - 1, ::ArrayDeque)
                    stack.add(crates[i])
                }
            }
        }
    }
    return stackList
}

fun buildProcedures(procedureString: String): List<Procedure> {
    return procedureString.lines().map { procedureLine ->
        val (move, from, to) = procedureLine.split("move ", " from ", " to ").drop(1).map(String::toInt)
        Procedure(move, from, to)
    }
}

fun concatStacksTopCrate(stacks: List<ArrayDeque<Char>>): String =
    stacks.map { it.firstOrNull() ?: "" }.joinToString("")

@JvmRecord
data class Procedure(
    val move: Int,
    val from: Int,
    val to: Int
)

interface Process {
    fun process(stacks: MutableList<ArrayDeque<Char>>)
}

class CrateMover9000(
    private val procedures: List<Procedure>
) : Process {
    override fun process(stacks: MutableList<ArrayDeque<Char>>) {
        procedures.forEach { (move, from, to) ->
            repeat(move) {
                stacks[to - 1].addFirst(stacks[from - 1].removeFirst())
            }
        }
    }
}

class CrateMover9001(
    private val procedures: List<Procedure>
) : Process {
    override fun process(stacks: MutableList<ArrayDeque<Char>>) {
        procedures.forEach { (move, from, to) ->
            val tempList = mutableListOf<Char>()
            repeat(move) {
                tempList.add(stacks[from - 1].removeFirst())
            }
            tempList.asReversed().forEach { stacks[to - 1].addFirst(it) }
        }
    }
}