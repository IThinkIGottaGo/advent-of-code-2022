package day07

import readString

/**
 * Answers from [Advent of Code 2022 Day 7 | Kotlin](https://youtu.be/Q819VW8yxFo)
 */
val input = readString("day07").split("$ ").drop(1).map { it.trim() }

fun main() {
    var current = VFolder("/", parent = null)
    val root = current
    for (i in input.drop(1)) {
        when (i.substring(0, 2)) {
            "ls" -> {
                current.children += i.lines().drop(1).asDirectoryListing(current)
            }

            "cd" -> {
                val path = i.removePrefix("cd ")
                current = if (path == "..") {
                    current.parent ?: error("illegal move!")
                } else {
                    current.children.first { it.name == path } as? VFolder ?: error("tried to cd into a file")
                }
            }
        }
    }

    println(root.findFolders(100_000).sumOf { it.getFileSize() })   // 1723892

    val totalDisk = 70_000_000
    val usedSpace = root.getFileSize()
    val freeSpace = totalDisk - usedSpace
    val neededSpace = 30_000_000
    val freeUpSpace = neededSpace - freeSpace

    println(root.findFolders(Int.MAX_VALUE).filter { it.getFileSize() > freeUpSpace }
        .minOf { it.getFileSize() })   // 8474158
}

private fun List<String>.asDirectoryListing(parent: VFolder): List<Node> {
    return map { elem ->
        if (elem.startsWith("dir")) {
            VFolder(elem.removePrefix("dir "), parent = parent)
        } else if (elem.first().isDigit()) {
            val (size, name) = elem.split(" ")
            VFile(name, size.toInt(), parent)
        } else {
            error("Malformed input")
        }
    }
}

sealed class Node(val name: String, val parent: VFolder?) {
    abstract fun getFileSize(): Int
}

class VFolder(name: String, val children: MutableList<Node> = mutableListOf(), parent: VFolder?) : Node(name, parent) {
    override fun getFileSize(): Int = children.sumOf { it.getFileSize() }

    fun findFolders(maxSize: Int): List<VFolder> {
        val children = children.filterIsInstance<VFolder>().flatMap { it.findFolders(maxSize) }
        return if (getFileSize() <= maxSize) {
            children + this
        } else {
            children
        }
    }
}

class VFile(name: String, val size: Int, parent: VFolder) : Node(name, parent) {
    override fun getFileSize(): Int = size
}