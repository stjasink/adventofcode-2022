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
        return routes.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    private fun findRoutes(valves: Map<String, Valve>): Int {
        fun takeTurn(atValveName: String, seenValves: Set<String>, openValves: Set<String>, timeLeft: Int): Int {
            if (timeLeft <= 0) {
                return 0
            }
            val scores = mutableListOf<Int>()
            val atValve = valves[atValveName]!!
            if (atValve.name !in openValves && atValve.flow > 0) {
                val remainingFlowThisValve = atValve.flow * (timeLeft - 1)
                atValve.exits.forEach {
                    val branchFlow = takeTurn(it, setOf(atValve.name), openValves + atValve.name, timeLeft - 2)
                    scores.add(remainingFlowThisValve + branchFlow)
                }
            }
            atValve.exits.forEach {
                if (it !in seenValves) {
                    val branchFlow = takeTurn(it, setOf(atValve.name) + atValve.name, openValves, timeLeft - 1)
                    scores.add(branchFlow)
                }
            }
            return scores.maxOrNull() ?: 0
        }

        return takeTurn("AA", emptySet(), emptySet(), 30)
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
        val exits: List<String>
    )
}
