import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-16.txt")
    val solver = Day16()
    runAndTime(solver, input)
}

class Day16 : Solver {

    override fun part1(input: List<String>): Long {
        val valves = parseInput(input)
        val routes = findRoutes(valves)
//        val testRoute = listOf(valves["AA"]!!, valves["DD"]!!, valves["DD"]!!.open(), valves["CC"]!!, valves["BB"]!!, valves["BB"]!!.open(), valves["AA"]!!, valves["II"]!!, valves["JJ"]!!, valves["JJ"]!!.open(), valves["II"]!!, valves["AA"]!!, valves["DD"]!!.open(), valves["EE"]!!, valves["FF"]!!, valves["GG"]!!, valves["HH"]!!, valves["HH"]!!.open(), valves["GG"]!!, valves["FF"]!!, valves["EE"]!!, valves["EE"]!!.open(), valves["DD"]!!, valves["CC"]!!, valves["CC"]!!.open(), valves["CC"]!!, valves["CC"]!!, valves["CC"]!!, valves["CC"]!!, valves["CC"]!!.open(), valves["CC"]!!)
//        val routes = listOf(testRoute)
        val routeFlows = routes.map { flowForRoute(it) }
        return routeFlows.max()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    private fun flowForRoute(route: List<Valve>): Long {
        val seenOpenValves = mutableSetOf<String>()
        val flowAndTimes = route.mapIndexed { time, valve ->
            val flowTime = if (valve.open && !seenOpenValves.contains(valve.name)) {
                seenOpenValves.add(valve.name)
                30 - time
            } else {
                0
            }
            valve.flow to flowTime
        }
        val totalFlow = flowAndTimes.map { (flow, time) ->
            flow * time
        }.sum()
        return totalFlow.toLong()
    }

    private fun findRoutes(valves: Map<String, Valve>): List<List<Valve>> {
        val completedRoutes = mutableListOf<List<Valve>>()
        val currentRoute = mutableListOf<Valve>()
        val currentlyOpen = mutableSetOf<String>()

        fun takeTurn(atValve: Valve) {
            currentRoute.add(atValve)
            if (currentRoute.size == 31) {
                // time is up, so route is complete - it's size 31 because we started with AA on time 0
                completedRoutes.add(currentRoute)
                currentRoute.removeLast()
                if (atValve.firstOpen) {
                    currentlyOpen.remove(atValve.name)
                }
            } else {
                // options are to open valve if not already open, or take exit
                // don't bother opening if flow is 0
                if (!atValve.open && atValve.flow != 0) {
                    // open the valve
                    currentlyOpen.add(atValve.name)
                    takeTurn(atValve.firstOpen())
                } else {
                    // take each exit in turn
                    atValve.exits.forEach { exit ->
                        val next = if (currentlyOpen.contains(exit)) {
                            valves[exit]!!.open()
                        } else {
                            valves[exit]!!
                        }
                        takeTurn(next)
                    }
                    currentRoute.removeLast()
                }
            }
        }

        takeTurn(valves["AA"]!!)
        return completedRoutes
    }

    private fun parseInput(input: List<String>): Map<String, Valve> {
        val valves = mutableMapOf<String, Valve>()
        val regex = Regex("Valve (.+) has flow rate=(.+); tunnel(s)? lead(s)? to valve(s)? (.+)")
        input.forEach { line ->
            val matches = regex.matchEntire(line)!!.groupValues
            val name = matches[1]
            val flow = matches[2].toInt()
            val exits = matches[6].split(",").map { it.trim() }
            val valve = Valve(name, flow, exits)
            valves[name] = valve
        }
        valves.forEach { (name, valve) ->
            println("$name -> $valve")
        }
        return valves
    }

    data class Valve(
        val name: String,
        val flow: Int,
        val exits: List<String>,
        val open: Boolean = false,
        val firstOpen: Boolean = false
    ) {
        fun open() = copy(open = true)
        fun firstOpen() = copy(open = true, firstOpen = true)
    }
}
