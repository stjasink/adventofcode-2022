import common.SolverString
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-05.txt")
    val solver = Day05()
    runAndTime(solver, input)
}

class Day05 : SolverString {

    override fun part1(input: List<String>): String {
        val numStacks = getNumStacks(input)
        val stacks = Array(numStacks) { mutableListOf<Char>() }
        var initialising = true
        input.forEach { line ->
            if (line.startsWith(" 1")) {
                stacks.forEach { stack ->
                    stack.reverse()
                }
                initialising = false
            }
            if (initialising) {
                val parts = line.chunked(4)
                parts.forEachIndexed { stackNum, part ->
                    if (part.startsWith("[")) {
                        stacks[stackNum].add(part[1])
                    }
                }
            } else {
                if (line.startsWith("move")) {
                    val regex = Regex("move (\\d*) from (\\d*) to (\\d*)")
                    val matches = regex.matchEntire(line)!!.groupValues
                    val numCrates = matches[1].toInt()
                    val sourceStackNum = matches[2].toInt() - 1
                    val destStackNum = matches[3].toInt() - 1
                    for (i in 1..numCrates) {
                        stacks[destStackNum].add(stacks[sourceStackNum].removeLast())
                    }
                }
            }
        }
        val answer = StringBuilder()
        stacks.forEach { stack ->
            if (stack.isNotEmpty()) {
                answer.append(stack.last())
            }
        }
        return answer.toString()
    }

    override fun part2(input: List<String>): String {
        val numStacks = getNumStacks(input)
        val stacks = Array(numStacks) { mutableListOf<Char>() }
        var initialising = true
        input.forEach { line ->
            if (line.startsWith(" 1")) {
                stacks.forEach { stack ->
                    stack.reverse()
                }
                initialising = false
            }
            if (initialising) {
                val parts = line.chunked(4)
                parts.forEachIndexed { stackNum, part ->
                    if (part.startsWith("[")) {
                        stacks[stackNum].add(part[1])
                    }
                }
            } else {
                if (line.startsWith("move")) {
                    val regex = Regex("move (\\d*) from (\\d*) to (\\d*)")
                    val matches = regex.matchEntire(line)!!.groupValues
                    val numCrates = matches[1].toInt()
                    val sourceStackNum = matches[2].toInt() - 1
                    val destStackNum = matches[3].toInt() - 1
                    val movingCrates = mutableListOf<Char>()
                    for (i in 1..numCrates) {
                        movingCrates.add(stacks[sourceStackNum].removeLast())
                    }
                    movingCrates.reversed().forEach { crate ->
                        stacks[destStackNum].add(crate)
                    }
                }
            }
        }
        val answer = StringBuilder()
        stacks.forEach { stack ->
            if (stack.isNotEmpty()) {
                answer.append(stack.last())
            }
        }
        return answer.toString()
    }

    private fun getNumStacks(input: List<String>): Int {
        input.forEach { line ->
            if (line.startsWith(" 1")) {
                return line.trim().last().digitToInt()
            }
        }
        throw IllegalStateException("No counts found")
    }
}
