package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var totalScore = 0
        val shapes = parseInputPart1(input)
        shapes.windowed(2, 2) { (myShape, elvesShape) ->
            totalScore += myShape.shapeScore + myShape.match(elvesShape).matchScore
        }
        return totalScore
    }

    fun part2(input: List<String>): Int {
        var totalScore = 0
        val (strategies, elvesShapes) = parseInputPart2(input)
        strategies.zip(elvesShapes) { strategy, elvesShape ->
            totalScore += strategy.counterChoose(elvesShape).shapeScore + strategy.matchScore
        }
        return totalScore
    }

    val testInput = readInput("day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02")
    println(part1(input))   // 12586
    println(part2(input))   // 13193
}

fun parseInputPart1(input: List<String>): List<Shape> =
    input.flatMap {
        it.split(' ').map(Shape::getShape).asReversed()
    }

fun parseInputPart2(input: List<String>): Pair<List<MatchResult>, List<Shape>> {
    val elvesShapes = mutableListOf<Shape>()
    val counterStrategies = mutableListOf<MatchResult>()
    input.forEach { line ->
        val (shapeStr, strategyStr) = line.split(' ')
        elvesShapes += Shape.getShape(shapeStr)
        counterStrategies += MatchResult.resolve(strategyStr)
    }
    return counterStrategies to elvesShapes
}

sealed interface Shape {
    val shapeScore: Int

    fun match(shape: Shape): MatchResult

    fun winChoose(): Shape

    fun loseChoose(): Shape

    fun drawChoose(): Shape = this

    companion object {
        @JvmStatic
        fun getShape(c: String): Shape =
            when (c) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissors
                else -> error("Unknown shape.")
            }
    }
}

object Rock : Shape {
    override val shapeScore = 1

    override fun match(shape: Shape): MatchResult =
        when (shape) {
            Rock -> Draw
            Paper -> Lose
            Scissors -> Win
        }

    override fun winChoose(): Shape = Paper

    override fun loseChoose(): Shape = Scissors
}

object Paper : Shape {
    override val shapeScore = 2

    override fun match(shape: Shape): MatchResult =
        when (shape) {
            Rock -> Win
            Paper -> Draw
            Scissors -> Lose
        }

    override fun winChoose(): Shape = Scissors

    override fun loseChoose(): Shape = Rock
}

object Scissors : Shape {

    override val shapeScore: Int = 3

    override fun match(shape: Shape): MatchResult =
        when (shape) {
            Rock -> Lose
            Paper -> Win
            Scissors -> Draw
        }

    override fun winChoose(): Shape = Rock

    override fun loseChoose(): Shape = Paper
}

sealed class MatchResult(val matchScore: Int) {

    abstract fun counterChoose(elvesShape: Shape): Shape

    companion object {
        @JvmStatic
        fun resolve(strategy: String): MatchResult =
            when (strategy) {
                "X" -> Lose
                "Y" -> Draw
                "Z" -> Win
                else -> error("Unknown strategy.")
            }
    }
}

object Win : MatchResult(6) {
    override fun counterChoose(elvesShape: Shape): Shape = elvesShape.winChoose()
}

object Lose : MatchResult(0) {
    override fun counterChoose(elvesShape: Shape): Shape = elvesShape.loseChoose()
}

object Draw : MatchResult(3) {
    override fun counterChoose(elvesShape: Shape): Shape = elvesShape.drawChoose()
}