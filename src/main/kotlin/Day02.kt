import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-02.txt")
    val solver = Day02()
    runAndTime(solver, input)
}

class Day02 : Solver {

    private val rockPoints = 1
    private val paperPoints = 2
    private val scissorsPoints = 3
    private val winPoints = 6
    private val drawPoints = 3
    private val losePoints = 0

    override fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val (opp, me) = line.split(" ")
            when (opp) {
                "A" -> {
                    when (me) {
                        "X" -> rockPoints + drawPoints
                        "Y" -> paperPoints + winPoints
                        "Z" -> scissorsPoints + losePoints
                        else -> throw IllegalStateException("Unknown choice $me")
                    }
                }
                "B" -> {
                    when (me) {
                        "X" -> rockPoints + losePoints
                        "Y" -> paperPoints + drawPoints
                        "Z" -> scissorsPoints + winPoints
                        else -> throw IllegalStateException("Unknown choice $me")
                    }
                }
                "C" -> {
                    when (me) {
                        "X" -> rockPoints + winPoints
                        "Y" -> paperPoints + losePoints
                        "Z" -> scissorsPoints + drawPoints
                        else -> throw IllegalStateException("Unknown choice $me")
                    }
                }
                else -> throw IllegalStateException("Unknown choice $opp")
            }
        }.toLong()
    }

    override fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val (opp, me) = line.split(" ")
            when (opp) {
                "A" -> {
                    when (me) {
                        "X" -> scissorsPoints + losePoints
                        "Y" -> rockPoints + drawPoints
                        "Z" -> paperPoints + winPoints
                        else -> throw IllegalStateException("Unknown choice $me")
                    }
                }
                "B" -> {
                    when (me) {
                        "X" -> rockPoints + losePoints
                        "Y" -> paperPoints + drawPoints
                        "Z" -> scissorsPoints + winPoints
                        else -> throw IllegalStateException("Unknown choice $me")
                    }
                }
                "C" -> {
                    when (me) {
                        "X" -> paperPoints + losePoints
                        "Y" -> scissorsPoints + drawPoints
                        "Z" -> rockPoints + winPoints
                        else -> throw IllegalStateException("Unknown choice $me")
                    }
                }
                else -> throw IllegalStateException("Unknown choice $opp")
            }
        }.toLong()
    }
}
