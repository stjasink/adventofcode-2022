import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-03.txt")
    val solver = Day03()
    runAndTime(solver, input)
}

class Day03 : Solver {

    override fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val comp1 = line.substring(0, line.length / 2).toCharArray().toSet()
            val comp2 = line.substring(line.length / 2).toCharArray().toSet()
            val common = comp1.intersect(comp2).first()
            itemPriority(common)
        }.toLong()
    }

    override fun part2(input: List<String>): Long {
        return input.windowed(3, 3).sumOf { group ->
            val elf1 = group[0].toCharArray().toSet()
            val elf2 = group[1].toCharArray().toSet()
            val elf3 = group[2].toCharArray().toSet()
            val common = elf1.intersect(elf2).intersect(elf3).first()
            itemPriority(common)
        }.toLong()
    }

    private val charsPoints = ('a'..'z') + ('A'..'Z')

    private fun itemPriority(common: Char): Int {
        return charsPoints.indexOf(common) + 1
    }
}
