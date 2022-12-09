package day08

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        input.forEachIndexed { iy, line ->
            line.forEachIndexed { ix, _ ->
                if (input.visible(ix, iy)) count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var highestScore = -1
        input.forEachIndexed { iy, line ->
            line.forEachIndexed { ix, _ ->
                val score = input.totalScenicScore(ix, iy)
                if (score > highestScore) highestScore = score
            }
        }
        return highestScore
    }

    val testInput = readInput("day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("day08")
    println(part1(input))   // 1803
    println(part2(input))   // 268912
}

fun List<String>.visible(x: Int, y: Int): Boolean =
    topVisible(x, y) || bottomVisible(x, y) || leftVisible(x, y) || rightVisible(x, y)

fun List<String>.topVisible(cx: Int, cy: Int): Boolean {
    var y = cy
    var visible = true
    while (--y >= 0 && visible) {
        visible = this[cy][cx] > this[y][cx]
    }
    return visible
}

fun List<String>.bottomVisible(cx: Int, cy: Int): Boolean {
    var y = cy
    var visible = true
    while (++y < size && visible) {
        visible = this[cy][cx] > this[y][cx]
    }
    return visible
}

fun List<String>.leftVisible(cx: Int, cy: Int): Boolean {
    var x = cx
    var visible = true
    while (--x >= 0 && visible) {
        visible = this[cy][cx] > this[cy][x]
    }
    return visible
}

fun List<String>.rightVisible(cx: Int, cy: Int): Boolean {
    var x = cx
    var visible = true
    while (++x < this[0].length && visible) {
        visible = this[cy][cx] > this[cy][x]
    }
    return visible
}

fun List<String>.totalScenicScore(x: Int, y: Int): Int =
    topScenicScore(x, y) * bottomScenicScore(x, y) * leftScenicScore(x, y) * rightScenicScore(x, y)


fun List<String>.topScenicScore(cx: Int, cy: Int): Int {
    var y = cy
    var score = 0
    while (--y >= 0) {
        ++score
        if (this[cy][cx] <= this[y][cx]) break
    }
    return score
}

fun List<String>.bottomScenicScore(cx: Int, cy: Int): Int {
    var y = cy
    var score = 0
    while (++y < size) {
        ++score
        if (this[cy][cx] <= this[y][cx]) break
    }
    return score
}

fun List<String>.leftScenicScore(cx: Int, cy: Int): Int {
    var x = cx
    var score = 0
    while (--x >= 0) {
        ++score
        if (this[cy][cx] <= this[cy][x]) break
    }
    return score
}

fun List<String>.rightScenicScore(cx: Int, cy: Int): Int {
    var x = cx
    var score = 0
    while (++x < this[0].length) {
        ++score
        if (this[cy][cx] <= this[cy][x]) break
    }
    return score
}