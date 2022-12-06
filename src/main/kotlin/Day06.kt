import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-06.txt")
    val solver = Day06()
    runAndTime(solver, input)
}

class Day06 : Solver {

    override fun part1(input: List<String>): Long {
        return findNonDuplicated(input[0], 4)
    }

    override fun part2(input: List<String>): Long {
        return findNonDuplicated(input[0], 14)
    }

    private fun findNonDuplicated(data: String, windowSize: Int): Long {
        data.windowed(windowSize).forEachIndexed { index, chars ->
            if (chars.toSet().size == windowSize) {
                return (windowSize + index).toLong()
            }
        }
        throw IllegalStateException("Not found")
    }
}
