import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-04.txt")
    val solver = Day04()
    runAndTime(solver, input)
}

class Day04 : Solver {

    override fun part1(input: List<String>): Long {
        var total = 0L
        input.forEach { line ->
            val (area1, area2) = areasFrom(line)
            val overlap = area1.intersect(area2)
            if (overlap == area2 || overlap == area1) {
                total += 1
            }
        }
        return total
    }

    override fun part2(input: List<String>): Long {
        var total = 0L
        input.forEach { line ->
            val (area1, area2) = areasFrom(line)
            val overlap = area1.intersect(area2)
            if (overlap.isNotEmpty()) {
                total += 1
            }
        }
        return total
    }

    private fun areasFrom(line: String): Pair<Set<Int>, Set<Int>> {
        val areas = line.split(",")
        val area1Strs = areas[0].split("-")
        val area2Strs = areas[1].split("-")
        val area1 = (area1Strs[0].toInt()..area1Strs[1].toInt()).toSet()
        val area2 = (area2Strs[0].toInt()..area2Strs[1].toInt()).toSet()
        return Pair(area1, area2)
    }
}
