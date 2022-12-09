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

        fun adjustYDiag(headNum: Int, tailNum: Int) {
            if (knotsAt[headNum].y - knotsAt[tailNum].y > 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y + 1)
            } else if (knotsAt[headNum].y - knotsAt[tailNum].y < 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y - 1)
            }
        }

        fun adjustXDiag(headNum: Int, tailNum: Int) {
            if (knotsAt[headNum].x - knotsAt[tailNum].x > 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x + 1)
            } else if (knotsAt[headNum].x - knotsAt[tailNum].x < 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x - 1)
            }
        }

        fun moveKnot(headNum: Int, tailNum: Int) {
            if (knotsAt[headNum].x - knotsAt[tailNum].x > 1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x + 1)
                adjustYDiag(headNum, tailNum)
            } else if (knotsAt[headNum].x - knotsAt[tailNum].x < -1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x - 1)
                adjustYDiag(headNum, tailNum)
            }
            if (knotsAt[headNum].y - knotsAt[tailNum].y > 1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y + 1)
                adjustXDiag(headNum, tailNum)
            } else if (knotsAt[headNum].y - knotsAt[tailNum].y < -1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y - 1)
                adjustXDiag(headNum, tailNum)
            }
        }

        input.forEach { line ->
            val dir = line.split(" ")[0]
            val amount = line.split(" ")[1].toInt()

            for (step in 1..amount) {
                when (dir) {
                    "R" -> {
                        knotsAt[0] = knotsAt[0].copy(x = knotsAt[0].x + 1)
                    }
                    "L" -> {
                        knotsAt[0] = knotsAt[0].copy(x = knotsAt[0].x - 1)
                    }
                    "U" -> {
                        knotsAt[0] = knotsAt[0].copy(y = knotsAt[0].y + 1)
                    }
                    "D" -> {
                        knotsAt[0] = knotsAt[0].copy(y = knotsAt[0].y - 1)
                    }
                }
                for (knotNum in 0..numKnots-2) {
                    moveKnot(knotNum, knotNum + 1)
                }
                tailVisited.add(knotsAt.last())
            }

        }

        return tailVisited.size.toLong()
    }

    data class Point(
        val x: Int,
        val y: Int
    )
}
