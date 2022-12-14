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
        val cave = Grid(input, false)
        return cave.fillWithSand()
    }

    override fun part2(input: List<String>): Long {
        val cave = Grid(input, true)
        return cave.fillWithSand()
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

    class Grid(input: List<String>, withFloor: Boolean) {
        private val rock: MutableSet<Point> = mutableSetOf()
        private val sand: MutableSet<Point> = mutableSetOf()
        private var maxRockY = 0
        private var minRockX = Int.MAX_VALUE
        private var maxRockX = 0

        init {
            input.forEach {
                addRockLines(it)
            }
            if (withFloor) {
                addFloor()
            }
        }

        fun fillWithSand(): Long {
//            print()
            var numSand = 0L
            for (i in 0..1_000_000) {
                val sandLanded = dropSand()
                if (sandLanded) {
//                    print()
                    numSand += 1
                } else {
                    return numSand
                }
            }
            throw IllegalStateException("Got to the end")
        }

        private fun addRockLines(lines: String) {
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

        private fun addRockLine(from: Point, to: Point) {
            if (from.y == to.y) {
                // if horizontal
                for (x in minOf(from.x, to.x) .. maxOf(from.x, to.x)) {
                    rock.add(Point(x, from.y))
                }
                minRockX = minOf(minRockX, minOf(from.x, to.x))
                maxRockX = maxOf(maxRockX, maxOf(from.x, to.x))
            } else {
                // if vertical
                for (y in minOf(from.y, to.y) .. maxOf(from.y, to.y)) {
                    rock.add(Point(from.x, y))
                }
                maxRockY = maxOf(maxRockY, maxOf(from.y, to.y))
            }
        }

        private fun addFloor() {
            val floorY = maxRockY + 2
            maxRockY = floorY
            val floorLen = 2 * floorY + 1
            val floorMinX = 500 - (floorLen/2) - 1
            val floorMaxX = 500 + (floorLen/2) + 1
            val floorStart = Point(floorMinX, floorY)
            val floorEnd = Point(floorMaxX, floorY)
            addRockLine(floorStart, floorEnd)
        }

        private fun dropSand(): Boolean {
            var sandAt = Point(500, 0)
            if (sand.contains(sandAt)) {
                // cave is full
                return false
            }
            for (y in 1..maxRockY) {
                val destinations = listOf(Point(sandAt.x, y), Point(sandAt.x - 1, y), Point(sandAt.x + 1, y))
                val validDestination = destinations.firstOrNull { isOpen(it) }
                if (validDestination == null) {
                    // nowhere for sand to go
                    sand.add(sandAt)
                    return true
                } else {
                    // move sand
                    sandAt = validDestination
                }
            }

            // sand fell into the void
            return false
        }

        private fun isOpen(point: Point): Boolean {
            return !(rock.contains(point) || sand.contains(point))
        }

        private fun print() {
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
