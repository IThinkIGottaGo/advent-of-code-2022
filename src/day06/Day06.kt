package day06

import readString

fun main() {
    fun part1(input: String): Int {
        return processInput(4, input)
    }

    fun part2(input: String): Int {
        return processInput(14, input)
    }

    val testInput = readString("day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readString("day06")
    println(part1(input))   // 1034
    println(part2(input))   // 2472
}

fun processInput(size: Int, line: String): Int {
    val buffer = RingBuffer<Char>(size)
    var i = 0
    line.takeWhile { c ->
        buffer.add(c)
        ++i
        (buffer.size != buffer.toSet().size)
    }
    return i
}

class RingBuffer<E>(
    override val size: Int,
    private val list: ArrayList<E> = ArrayList(size)
) : MutableList<E> by list {
    private var currentIndex = 0
        set(value) {
            field = value % size
        }

    override fun add(element: E): Boolean {
        if (list.getOrNull(currentIndex) == null) {
            ++currentIndex
            return list.add(element)
        }
        list[currentIndex++] = element
        return true
    }
}