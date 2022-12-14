import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-14.txt")
    val solver = Day14()
    runAndTime(solver, input)
}

class Day14 : Solver {

    override fun part1(input: List<String>): Long {
        val cave = Grid(input)
        cave.print()
        var numSand = 0L
        for (i in 0..1_000_000) {
            val sandLanded = cave.dropSand()
            if (sandLanded) {
//                cave.print()
                numSand += 1
            } else {
                return numSand
            }
        }
        throw IllegalStateException("Got to the end")
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    data class Point(
        val x: Int,
        val y: Int
    ) {
        companion object {
            fun from(str: String): Point {
                val (newX, newY) = str.split(",").map { it.toInt() }
                return Point(newX, newY)
            }
        }
    }

    class Grid(input: List<String>) {
        val rock: MutableSet<Point> = mutableSetOf()
        val sand: MutableSet<Point> = mutableSetOf()
        var maxRockY = 0
        var minRockX = Int.MAX_VALUE
        var maxRockX = 0

        init {
            input.forEach {
                addRockLines(it)
            }
        }

        fun addRockLines(lines: String) {
            lines.split("->")
                .map {it.trim()}
                .windowed(2)
                .forEach {
                    val first = Point.from(it[0])
                    val second = Point.from(it[1])
                    // first is always higher X and lower Y
                    addRockLine(first, second)
                }
        }

        fun addRockLine(from: Point, to: Point) {
            if (from.y == to.y) {
                // if horizontal
                // to is lower x
                for (x in minOf(from.x, to.x) .. maxOf(from.x, to.x)) {
                    rock.add(Point(x, from.y))
                }
                minRockX = minOf(minRockX, minOf(from.x, to.x))
                maxRockX = maxOf(maxRockX, maxOf(from.x, to.x))
            } else {
                // if vertical
                // to is higher y
                for (y in minOf(from.y, to.y) .. maxOf(from.y, to.y)) {
                    rock.add(Point(from.x, y))
                }
                // remember max y
                maxRockY = maxOf(maxRockY, maxOf(from.y, to.y))
            }

        }

        fun dropSand(): Boolean {
            var sandAt = Point(500, 0)

            for (y in 1..maxRockY) {
                val sandDown = Point(sandAt.x, y)
                val sandDownLeft = Point(sandAt.x - 1, y)
                val sandDownRight = Point(sandAt.x + 1, y)
                if (isOpen(sandDown)) {
                    sandAt = sandDown
                } else if (isOpen(sandDownLeft)) {
                    sandAt = sandDownLeft
                } else if (isOpen(sandDownRight)) {
                    sandAt = sandDownRight
                } else {
                    // nowhere for sand to go
                    sand.add(sandAt)
                    return true
                }
            }

            return false
        }

        fun isOpen(point: Point): Boolean {
            return !(rock.contains(point) || sand.contains(point))
        }

        fun print() {
            for (y in 0..maxRockY) {
                print("$y ")
                for (x in minRockX..maxRockX) {
                    val point = Point(x, y)
                    if (rock.contains(point)) {
                        print("#")
                    } else if (sand.contains(point)) {
                        print("o")
                    } else {
                        print(".")
                    }
                }
                println()
            }
            println()
        }
    }
}
