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
        var headAt = Point(0, 0)
        var tailAt = Point(0, 0)
        val tailVisited = mutableSetOf<Point>()

        fun moveTail() {
            if (headAt.x - tailAt.x > 1) {
                tailAt = tailAt.copy(x = tailAt.x + 1)
                if (tailAt.y != headAt.y) {
                    tailAt = tailAt.copy(y = headAt.y)
                }
            } else if (headAt.x - tailAt.x < -1) {
                tailAt = tailAt.copy(x = tailAt.x - 1)
                if (tailAt.y != headAt.y) {
                    tailAt = tailAt.copy(y = headAt.y)
                }
            }
            if (headAt.y - tailAt.y > 1) {
                tailAt = tailAt.copy(y = tailAt.y + 1)
                if (tailAt.x != headAt.x) {
                    tailAt = tailAt.copy(x = headAt.x)
                }
            } else if (headAt.y - tailAt.y < -1) {
                tailAt = tailAt.copy(y = tailAt.y - 1)
                if (tailAt.x != headAt.x) {
                    tailAt = tailAt.copy(x = headAt.x)
                }
            }
        }

        input.forEach { line ->
            val dir = line.split(" ")[0]
            val amount = line.split(" ")[1].toInt()

            for (step in 1..amount) {
                when (dir) {
                    "R" -> {
                        headAt = headAt.copy(x = headAt.x + 1)
                    }
                    "L" -> {
                        headAt = headAt.copy(x = headAt.x - 1)
                    }
                    "U" -> {
                        headAt = headAt.copy(y = headAt.y + 1)
                    }
                    "D" -> {
                        headAt = headAt.copy(y = headAt.y - 1)
                    }
                }
                moveTail()
                tailVisited.add(tailAt)
            }
        }

        return tailVisited.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        val knotsAt = Array(10) {Point(0, 0)}.toMutableList()
        val nineVisited = mutableSetOf<Point>()

        fun adjustY(headNum: Int, tailNum: Int) {
            if (knotsAt[headNum].y - knotsAt[tailNum].y > 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y + 1)
            } else if (knotsAt[headNum].y - knotsAt[tailNum].y < 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y - 1)
            }
        }

        fun adjustX(headNum: Int, tailNum: Int) {
            if (knotsAt[headNum].x - knotsAt[tailNum].x > 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x + 1)
            } else if (knotsAt[headNum].x - knotsAt[tailNum].x < 0) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x - 1)
            }
        }

        fun moveKnot(headNum: Int, tailNum: Int) {
            if (knotsAt[headNum].x - knotsAt[tailNum].x > 1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x + 1)
                adjustY(headNum, tailNum)
            } else if (knotsAt[headNum].x - knotsAt[tailNum].x < -1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(x = knotsAt[tailNum].x - 1)
                adjustY(headNum, tailNum)
            }
            if (knotsAt[headNum].y - knotsAt[tailNum].y > 1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y + 1)
                adjustX(headNum, tailNum)
            } else if (knotsAt[headNum].y - knotsAt[tailNum].y < -1) {
                knotsAt[tailNum] = knotsAt[tailNum].copy(y = knotsAt[tailNum].y - 1)
                adjustX(headNum, tailNum)
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
                for (knotNum in 0..8) {
                    moveKnot(knotNum, knotNum + 1)
                }
                nineVisited.add(knotsAt[9])
//                printPos(26, knotsAt)
            }

        }

        return nineVisited.size.toLong()
    }

    data class Point(
        val x: Int,
        val y: Int
    )

    private fun printPos(size: Int, knotsAt: List<Point>) {
        for (y in 6 downTo -0) {
            for (x in 0 until 6) {
                val knotNum = knotsAt.indexOfFirst { it.x == x && it.y == y }
                if (knotNum == -1) {
                    print(".")
                } else if (knotNum == 0) {
                    print("H")
                } else {
                    print(knotNum)
                }
            }
            println()
        }
        println()
    }
}
