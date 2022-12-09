import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    val input = loadInput("day-09.txt")
    val solver = Day09()
    runAndTime(solver, input)
}

class Day09 : Solver {

    override fun part1(input: List<String>): Long {
        return calculateKnots(input, 2)
    }

    override fun part2(input: List<String>): Long {
        return calculateKnots(input, 10)
    }

    private fun calculateKnots(input: List<String>, numKnots: Int): Long {
        val knotsAt = Array(numKnots) {Point(0, 0)}.toMutableList()
        val tailVisited = mutableSetOf<Point>()

        fun adjustYDiag(num: Int) {
            val yDiff = knotsAt[num].y - knotsAt[num+1].y
            if (yDiff.absoluteValue > 0) {
                knotsAt[num+1] = knotsAt[num+1].moveY(yDiff.sign)
            }
        }

        fun adjustXDiag(num: Int) {
            val xDiff = knotsAt[num].x - knotsAt[num+1].x
            if (xDiff.absoluteValue > 0) {
                knotsAt[num+1] = knotsAt[num+1].moveX(xDiff.sign)
            }
        }

        fun moveNextKnot(num: Int) {
            val xDiff = knotsAt[num].x - knotsAt[num+1].x
            val yDiff = knotsAt[num].y - knotsAt[num+1].y
            if (xDiff.absoluteValue > 1) {
                knotsAt[num+1] = knotsAt[num+1].moveX(xDiff.sign)
                adjustYDiag(num)
            } else if (yDiff.absoluteValue > 1) {
                knotsAt[num+1] = knotsAt[num+1].moveY(yDiff.sign)
                adjustXDiag(num)
            }
        }

        input.forEach { line ->
            val dir = line.split(" ")[0]
            val amount = line.split(" ")[1].toInt()

            for (step in 1..amount) {
                when (dir) {
                    "R" -> {
                        knotsAt[0] = knotsAt[0].moveX(1)
                    }
                    "L" -> {
                        knotsAt[0] = knotsAt[0].moveX(-1)
                    }
                    "U" -> {
                        knotsAt[0] = knotsAt[0].moveY(1)
                    }
                    "D" -> {
                        knotsAt[0] = knotsAt[0].moveY(-1)
                    }
                }
                for (knotNum in 0..numKnots-2) {
                    moveNextKnot(knotNum)
                }
                tailVisited.add(knotsAt.last())
            }

        }

        return tailVisited.size.toLong()
    }

    data class Point(
        val x: Int,
        val y: Int
    ) {
        fun moveX(amt: Int) = this.copy(x = x + amt)
        fun moveY(amt: Int) = this.copy(y = y + amt)
    }
}
