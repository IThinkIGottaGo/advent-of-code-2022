package day07

import readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val structure = parseInput(input)
        structure.accept(PrintlnContentVisitor())
        println("——————————————————————————————————————")
        val sizeVisitor = TotalSizeVisitor()
        structure.accept(sizeVisitor)
        return sizeVisitor.validDirSizes.sum()
    }

    fun part2(input: List<String>): Int {
        val structure = parseInput(input)
        val needToFreeSize = 30000000 - (70000000 - structure.size)
        val sizeVisitor = FreeSizeVisitor()
        structure.accept(sizeVisitor)
        var minValidFreeSize = 0
        while (sizeVisitor.dirSizes.isNotEmpty()) {
            val s = sizeVisitor.dirSizes.poll()
            if (s >= needToFreeSize) minValidFreeSize = s
            else break
        }
        return minValidFreeSize
    }

    val testInput = readInput("day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("day07")
    println(part1(input))   // 1723892
    println(part2(input))   // 8474158
}


fun parseInput(input: List<String>): Visitable {
    var structure: Visitable? = null
    lateinit var current: Directory
    var name: String
    var size: Int

    var index = 0
    while (index < input.size) {
        val line = input[index]
        when {
            line.startsWith("$ cd") -> {
                if (line.contains("..")) {
                    current = current.parent!!
                } else {
                    name = line.substringAfterLast(" ")
                    structure?.apply {
                        current = current.contents.first { it.name == name } as Directory
                    } ?: run {
                        current = Directory(name)
                        structure = current
                    }
                }
                ++index
            }

            line.startsWith("$ ls") -> {
                var detailsLine = input[++index]
                do {
                    name = detailsLine.substringAfter(" ")
                    if (detailsLine.startsWith("dir")) {
                        current.add(Directory(name, current))
                    } else {
                        size = detailsLine.substringBefore(" ").toInt()
                        current.add(File(name, size, current))
                    }
                } while (++index < input.size && !input[index].also { detailsLine = it }.startsWith("$"))
            }

            else -> error("unknown command!")
        }
    }
    return structure!!
}

interface Visitable {
    val name: String
    val size: Int
    var parent: Directory?

    fun accept(visitor: Visitor)
}

interface Visitor {
    fun visit(file: File)

    fun visit(dir: Directory)
}

data class Directory(
    override val name: String,
    override var parent: Directory? = null,
) : Visitable {
    val contents: MutableList<Visitable> = LinkedList()

    override val size: Int by lazy {
        contents.sumOf { it.size }
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    fun add(visitable: Visitable) {
        contents.add(visitable)
    }
}

data class File(
    override val name: String,
    override val size: Int,
    override var parent: Directory?,
) : Visitable {

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}

class PrintlnContentVisitor : Visitor {
    var depth = 0

    override fun visit(file: File) {
        repeat(depth - 2) { print(" ") }
        println("└─file (${file.name}, ${file.size})")
    }

    override fun visit(dir: Directory) {
        repeat(depth) { print(" ") }
        println("|dir ${dir.name}")
        depth += 2
        dir.contents.forEach {
            it.accept(this)
            if (it is Directory) depth -= 2
        }
    }
}

class TotalSizeVisitor : Visitor {
    private val limit = 100000
    val validDirSizes = mutableListOf<Int>()

    override fun visit(file: File) {
        // nothing here
    }

    override fun visit(dir: Directory) {
        dir.contents.forEach {
            it.accept(this)
        }
        val s = dir.size
        if (s <= limit) {
            validDirSizes.add(s)
        }
    }
}

class FreeSizeVisitor : Visitor {
    val dirSizes = PriorityQueue<Int>(Comparator.reverseOrder())

    override fun visit(file: File) {
        // nothing here
    }

    override fun visit(dir: Directory) {
        dir.contents.forEach {
            it.accept(this)
        }
        val s = dir.size
        dirSizes.offer(s)
    }
}