package day11

import readString
import java.math.BigInteger
import java.util.*

fun main() {
    fun part1(input: String): Long {
        val monkeyList = parseInput(input)
        repeat(20) {                 // 20 rounds
            monkeyList.forEach { monkey ->
                monkey.inspect(monkeyList)      // each turn
            }
        }
        return getTwoMostActiveMonkeysResult(monkeyList)
    }

    fun part2(input: String): Long {
        // program will run nearly infinite in this way, can not solve this part in time.
        val monkeyList = parseInput(input)
        repeat(10000) {
            monkeyList.forEach { monkey ->
                monkey.inspect(monkeyList, soWorriedCannotReliefBy3 = true)
            }
        }
        return getTwoMostActiveMonkeysResult(monkeyList)
    }

    val testInput = readString("day11_test")
    check(part1(testInput) == 10605L)
//    check(part2(testInput) == 2713310158)

    val input = readString("day11")
    println(part1(input))   // 118674
//    println(part2(input))   //
}

fun parseInput(input: String): List<Monkey> {
    val monkeyList = mutableListOf<Monkey>()
    val monkeys = input.split("\n\n")
    monkeys.forEach { monkey ->
        val lines = monkey.lines()
        monkeyList.add(
            Monkey(
                lines[1].substringAfterLast(":").split(",").mapTo(LinkedList()) { it.trim().toBigInteger() },
                lines[2].substringAfterLast("=").trim(),
                lines.subList(3, lines.size).map(String::trim)
            )
        )
    }
    return monkeyList
}

fun getTwoMostActiveMonkeysResult(monkeyList: List<Monkey>): Long {
    val (most, secondary) = monkeyList.sortedByDescending { it.inspectionCounts }.take(2)
    return most.inspectionCounts * secondary.inspectionCounts
}

data class Monkey(
    val holdingItems: Queue<BigInteger>,
    val operation: String,
    val test: List<String>,
    var inspectionCounts: Long = 0
) {
    private val _operation: (BigInteger) -> (BigInteger) = operationParse()
    private val _test: (BigInteger) -> (Int) = testParse()

    fun inspect(monkeyList: List<Monkey>, soWorriedCannotReliefBy3: Boolean = false) {
        while (holdingItems.isNotEmpty()) {
            ++inspectionCounts
            var item = holdingItems.poll()
            item = if (soWorriedCannotReliefBy3) _operation(item) else _operation(item) / 3.toBigInteger()
            throwTo(monkeyList[_test(item)], item)
        }
    }

    private fun operationParse(): (BigInteger) -> (BigInteger) {
        var opArg: BigInteger
        val (_, arg2, arg3) = operation.split(" ")
        return when (arg2) {
            "+" -> {
                {
                    opArg = getOldOrNum(it, arg3)
                    it + opArg
                }
            }

            "*" -> {
                {
                    opArg = getOldOrNum(it, arg3)
                    it * opArg
                }
            }

            else -> error("unexpected operation!")
        }
    }

    private fun testParse(): (BigInteger) -> (Int) {
        val divider = test[0].substringAfterLast(" ").toBigInteger()
        val trueCondition = test[1].substringAfterLast(" ").toInt()
        val falseCondition = test[2].substringAfterLast(" ").toInt()
        return { if (it % divider == BigInteger.ZERO) trueCondition else falseCondition }
    }

    private fun throwTo(other: Monkey, item: BigInteger) {
        other.holdingItems.offer(item)
    }

    private fun getOldOrNum(old: BigInteger, arg3: String): BigInteger =
        if (arg3 == "old") old else arg3.toBigInteger()
}