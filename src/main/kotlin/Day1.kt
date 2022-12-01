import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-1.txt")
    val solver = Day1()
    runAndTime(solver, input)
}

class Day1 : Solver {

    override fun part1(input: List<String>): Long {
        val totals = addCalorieList(input)
        return totals.max()
    }

    override fun part2(input: List<String>): Long {
        val totals = addCalorieList(input)
        return totals.sortedDescending().subList(0, 3).sum()
    }

    private fun addCalorieList(input: List<String>): MutableList<Long> {
        val totals = mutableListOf<Long>()

        var elfTotal = 0L
        input.forEach { calString ->
            if (calString.isEmpty()) {
                totals.add(elfTotal)
                elfTotal = 0L
            } else {
                val cals = calString.toLong()
                elfTotal += cals
            }
        }
        return totals
    }

}
