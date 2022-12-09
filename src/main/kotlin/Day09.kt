import common.Solver
import common.runAndTime
import common.loadInput

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
            if (knotsAt[num].y - knotsAt[num+1].y > 0) {
                knotsAt[num+1] = knotsAt[num+1].incY()
            } else if (knotsAt[num].y - knotsAt[num+1].y < 0) {
                knotsAt[num+1] = knotsAt[num+1].decY()
            }
        }

        fun adjustXDiag(num: Int) {
            if (knotsAt[num].x - knotsAt[num+1].x > 0) {
                knotsAt[num+1] = knotsAt[num+1].incX()
            } else if (knotsAt[num].x - knotsAt[num+1].x < 0) {
                knotsAt[num+1] = knotsAt[num+1].decX()
            }
        }

        fun moveNextKnot(num: Int) {
            if (knotsAt[num].x - knotsAt[num+1].x > 1) {
                knotsAt[num+1] = knotsAt[num+1].incX()
                adjustYDiag(num)
            } else if (knotsAt[num].x - knotsAt[num+1].x < -1) {
                knotsAt[num+1] = knotsAt[num+1].decX()
                adjustYDiag(num)
            }
            if (knotsAt[num].y - knotsAt[num+1].y > 1) {
                knotsAt[num+1] = knotsAt[num+1].incY()
                adjustXDiag(num)
            } else if (knotsAt[num].y - knotsAt[num+1].y < -1) {
                knotsAt[num+1] = knotsAt[num+1].decY()
                adjustXDiag(num)
            }
        }

        input.forEach { line ->
            val dir = line.split(" ")[0]
            val amount = line.split(" ")[1].toInt()

            for (step in 1..amount) {
                when (dir) {
                    "R" -> {
                        knotsAt[0] = knotsAt[0].incX()
                    }
                    "L" -> {
                        knotsAt[0] = knotsAt[0].decX()
                    }
                    "U" -> {
                        knotsAt[0] = knotsAt[0].incY()
                    }
                    "D" -> {
                        knotsAt[0] = knotsAt[0].decY()
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
        fun incX() = this.copy(x = x + 1)
        fun decX() = this.copy(x = x - 1)
        fun incY() = this.copy(y = y + 1)
        fun decY() = this.copy(y = y - 1)
    }
}
