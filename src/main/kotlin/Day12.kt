import common.Solver
import common.runAndTime
import common.loadInput
import java.util.*

fun main() {
    val input = loadInput("day-12.txt")
    val solver = Day12()
    runAndTime(solver, input)
}

class Day12 : Solver {

    override fun part1(input: List<String>): Long {
        val grid = Grid(input)
        val distance = grid.findDistance(grid.findStart(input))
        return distance.toLong()
    }

    override fun part2(input: List<String>): Long {
        val potentialStarts = mutableListOf<Point>()
        for (y in input.indices) {
            for (x in 0 until input[0].length) {
                if (input[y][x] == 'a' || input[y][x] == 'S') {
                    potentialStarts.add(Point(x, y))
                }
            }
        }
        val distances = potentialStarts.map { start ->
            val grid = Grid(input)
            grid.findDistance(start)
        }
        return distances.min().toLong()
    }

    data class Point(
        val x: Int,
        val y: Int
    )

    class Grid(input: List<String>) {
        private val heights: Map<Point, Int>
        private val visited: MutableSet<Point> = mutableSetOf()
        private val tentativeDistances: MutableMap<Point, Int> = mutableMapOf()
        private val calculatedUnvisitedDistances = PriorityQueue<Pair<Int, Point>> { o1, o2 -> o1.first.compareTo(o2.first) }
        private val maxY: Int
        private val maxX: Int
        private val end = findEnd(input)

        init {
            heights = mutableMapOf()
            input.forEachIndexed { y, line ->
                line.toCharArray().forEachIndexed { x, c ->
                    when {
                        (c == 'S') -> heights[Point(x, y)] = 'a'.code
                        (c == 'E') -> heights[Point(x, y)] = 'z'.code
                        else -> heights[Point(x, y)] = c.code
                    }
                }
            }

            maxY = heights.keys.maxOf { it.y }
            maxX = heights.keys.maxOf { it.x }

            heights.forEach { (key, _) ->
                tentativeDistances[key] = Int.MAX_VALUE
            }
        }

        fun findDistance(start: Point): Int {
            tentativeDistances[start] = 0

            setNeighbourDistancesFor(start)
            while (visited.size < heights.size) {
                if (calculatedUnvisitedDistances.isEmpty()) {
                    // there is no route to the end but that is okay, just return large number
                    return Int.MAX_VALUE
                }
                val point = calculatedUnvisitedDistances.remove().second
                if (point == end) {
                    return tentativeDistances[point]!!
                }
                setNeighbourDistancesFor(point)
                visited.add(point)
            }

            throw IllegalStateException("Could not get to the end")
        }

        private fun printGrid() {
            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    val p = Point(x, y)
                    val dist = tentativeDistances[p]
                    if (dist == Int.MAX_VALUE) {
                        print(" X  ")
                    } else {
                        print(String.format("%3d ", dist!!))
                    }
                }
                println()
            }
            println()
        }

        private fun setNeighbourDistancesFor(point: Point) {
            point.getNeighbours().forEach { neighbour ->
                if (!visited.contains(neighbour)) {
                    val neighbourDistance = tentativeDistances[point]!! + 1
                    if (neighbourDistance < tentativeDistances[neighbour]!!) {
                        tentativeDistances[neighbour] = neighbourDistance
                        calculatedUnvisitedDistances.add(Pair(neighbourDistance, neighbour))
                    }
                }
            }
        }

        private fun Point.getNeighbours(): List<Point> {
            val neighbours = mutableListOf<Point>()
            if (x > 0) neighbours.add(Point(x-1, y))
            if (y > 0) neighbours.add(Point(x, y-1))
            if (x < maxX) neighbours.add(Point(x+1, y))
            if (y < maxY) neighbours.add(Point(x, y+1))

            return neighbours.filter { n ->
                heights[n]!! <= heights[this]!! + 1
            }
        }

        fun findStart(input: List<String>): Point {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c == 'S') {
                        return Point(x, y)
                    }
                }
            }
            throw IllegalStateException("Did not find Start")
        }

        private fun findEnd(input: List<String>): Point {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c == 'E') {
                        return Point(x, y)
                    }
                }
            }
            throw IllegalStateException("Did not find Start")
        }
    }
}
