import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.abs

fun main() {
    val input = loadInput("day-15.txt")
    val solver = Day15()
    runAndTime(solver, input)
}

class Day15 : Solver {

    override fun part1(input: List<String>): Long {
        val cave = Grid(input)
//        return cave.noBeaconsOnRow2(2000000)
        return cave.noBeaconsOnRow2(10)
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    class Grid(input: List<String>) {
        private val sensors: MutableSet<Point> = mutableSetOf()
        private val sensorDistances: MutableMap<Point, Long> = mutableMapOf()
        private val beacons: MutableSet<Point> = mutableSetOf()

        init {
            val regex = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")
            input.forEach { line ->
                val matches = regex.matchEntire(line)!!.groupValues
                val sensorAt = Point(matches[1].toLong(), matches[2].toLong())
                val beaconAt = Point(matches[3].toLong(), matches[4].toLong())
                sensors.add(sensorAt)
                sensorDistances[sensorAt] = sensorAt.distanceTo(beaconAt)
                beacons.add(beaconAt)
//                println(line)
            }
//            print()
        }


        fun noBeaconsOnRow2(rowNum: Long): Long {
            var total = 0L
            val minX = (sensors + beacons).minOf { it.x }
            val maxX = (sensors + beacons).maxOf { it.x }

            for (x in minX..maxX) {
                val point = Point(x, rowNum)
                if (!beacons.contains(point) && !sensors.contains(point)) {
                    val withinRanges = sensorDistances.map { (sensor, dist) ->
                        sensor.distanceTo(point) <= dist
                    }
                    if (withinRanges.contains(true)) {
                        total += 1
                    }
                }
            }
            return total
        }

        private fun print() {
            val minY = (sensors + beacons).minOf { it.y }
            val maxY = (sensors + beacons).maxOf { it.y }
            val minX = (sensors + beacons).minOf { it.x }
            val maxX = (sensors + beacons).maxOf { it.x }
            for (y in minY..maxY) {
                print("%2d ".format(y))
                for (x in minX..maxX) {
                    val point = Point(x, y)
                    if (sensors.contains(point)) {
                        print("S")
                    } else if (beacons.contains(point)) {
                        print("B")
                    } else {
                        print(".")
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
