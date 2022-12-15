import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = loadInput("day-15.txt")
    val solver = Day15()
    runAndTime(solver, input)
}

class Day15 : Solver {

    override fun part1(input: List<String>): Long {
        val cave = Grid(input)

        return cave.noBeaconsOnRow(2000000)
//        return cave.noBeaconsOnRow2(10)
    }

    override fun part2(input: List<String>): Long {
        val cave = Grid(input)
        val maxRow = 4000000L
//        val maxRow = 20L
        val found = cave.findGap(maxRow)
        return found.x * 4000000 + found.y
    }

    class Grid(input: List<String>) {
        private val sensorsAndBeacons: MutableList<Pair<Point, Point>> = mutableListOf()

        init {
            val regex = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")
            input.forEach { line ->
                val matches = regex.matchEntire(line)!!.groupValues
                val sensorAt = Point(matches[1].toLong(), matches[2].toLong())
                val beaconAt = Point(matches[3].toLong(), matches[4].toLong())
                sensorsAndBeacons.add(Pair(sensorAt, beaconAt))
                val reline = "Sensor at x=${sensorAt.x}, y=${sensorAt.y}: closest beacon is at x=${beaconAt.x}, y=${beaconAt.y}"
                if (line != reline) {
                    println("Parsing broke!")
                }
            }
//            print()
        }


        fun noBeaconsOnRow(rowNum: Long): Long {
            val noBeacons = mutableSetOf<Point>()
            sensorsAndBeacons.forEach { (sensor, beacon) ->
                val distance = sensor.distanceTo(beacon)
                val distanceToRow = abs(sensor.y - rowNum)
                val width = distance - distanceToRow
                for (x in sensor.x - width..sensor.x + width) {
                    val point = Point(x, rowNum)
                    if (beacon != point) {
                        noBeacons.add(point)
                    }
                }
            }
            return noBeacons.size.toLong()
        }

        fun findGap(maxCoord: Long): Point {
            for (rowNum in 0..maxCoord) {
                println("Row $rowNum")
                val rangesCovered = mutableListOf<LongRange>()
                sensorsAndBeacons.forEach { (sensor, beacon) ->
                    val distance = sensor.distanceTo(beacon)
                    val distanceToRow = abs(sensor.y - rowNum)
                    val width = distance - distanceToRow
                    if (width > 0) {
                        rangesCovered.add(max(sensor.x - width, 0)..min(sensor.x + width, maxCoord))
                    }
                }
                val sortedRanges = rangesCovered.sortedBy { it.first }
                var previousHighest = sortedRanges.first().last
                sortedRanges.drop(1).forEach { range ->
                    if (range.first > previousHighest) {
                        // we have a coverage gap so must be the missing beacon
                        return Point(previousHighest+1, rowNum)
                    }
                    if (range.last > previousHighest) {
                        previousHighest = range.last
                    }
                }
            }
            throw IllegalStateException("Not found")
        }

        private fun print() {
            val minY = sensorsAndBeacons.minOf { min(it.first.y, it.second.y) }
            val maxY = sensorsAndBeacons.maxOf { max(it.first.y, it.second.y) }
            val minX = sensorsAndBeacons.minOf { min(it.first.x, it.second.x) }
            val maxX = sensorsAndBeacons.maxOf { max(it.first.x, it.second.x) }
            val sensors = sensorsAndBeacons.map { it.first }
            val beacons = sensorsAndBeacons.map { it.second }
            for (y in 0..maxY) {
                print("%2d ".format(y))
                for (x in 0..maxX) {
                    val point = Point(x, y)
                    if (sensors.contains(point)) {
                        print("S")
                    } else if (beacons.contains(point)) {
                        print("B")
                    } else {
                        val inRange = sensorsAndBeacons.map { (sensor, beacon) ->
                            val range = sensor.distanceTo(beacon)
                            sensor.distanceTo(point) <= range
                        }.contains(true)
                        if (inRange) {
                            print("#")
                        } else {
                            print(".")
                        }
                    }
                }
                println()
            }
            println()
        }

    }

    data class Point(
        val x: Long,
        val y: Long
    ) {
        fun distanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)
    }
}
