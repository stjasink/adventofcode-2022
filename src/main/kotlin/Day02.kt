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
        var total = 0L
        input.forEach { line ->
            val (opp, me) = line.split(" ")
            when (opp) {
                "A" -> {
                    when (me) {
                        "X" -> total += rockPoints + drawPoints
                        "Y" -> total += paperPoints + winPoints
                        "Z" -> total += scissorsPoints + losePoints
                    }
                }
                "B" -> {
                    when (me) {
                        "X" -> total += rockPoints + losePoints
                        "Y" -> total += paperPoints + drawPoints
                        "Z" -> total += scissorsPoints + winPoints
                    }
                }
                "C" -> {
                    when (me) {
                        "X" -> total += rockPoints + winPoints
                        "Y" -> total += paperPoints + losePoints
                        "Z" -> total += scissorsPoints + drawPoints
                    }
                }
            }
        }
        return total
    }

    override fun part2(input: List<String>): Long {
        var total = 0L
        input.forEach { line ->
            val (opp, me) = line.split(" ")
            when (opp) {
                "A" -> {
                    when (me) {
                        "X" -> total += scissorsPoints + losePoints
                        "Y" -> total += rockPoints + drawPoints
                        "Z" -> total += paperPoints + winPoints
                    }
                }
                "B" -> {
                    when (me) {
                        "X" -> total += rockPoints + losePoints
                        "Y" -> total += paperPoints + drawPoints
                        "Z" -> total += scissorsPoints + winPoints
                    }
                }
                "C" -> {
                    when (me) {
                        "X" -> total += paperPoints + losePoints
                        "Y" -> total += scissorsPoints + drawPoints
                        "Z" -> total += rockPoints + winPoints
                    }
                }
            }
        }
        return total
    }
}
