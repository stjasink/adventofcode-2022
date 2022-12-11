import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-11.txt")
    val solver = Day11()
    runAndTime(solver, input)
}

class Day11 : Solver {

    override fun part1(input: List<String>): Long {
        val numMonkeys = input.filter { it.startsWith("Monkey ") }.size
        val monkeyItems = List(numMonkeys) { mutableListOf<Long>() }.toMutableList()
        val monkeyInspected = List(numMonkeys) { 0L }.toMutableList()

        // initialise items
        input.chunked(7).forEachIndexed { monkeyNum, monkeyLines ->
            monkeyItems[monkeyNum] = monkeyLines[1].trim()
                .substringAfter("Starting items: ")
                .split(",")
                .map { it.trim() }
                .map { it.toLong() }
                .toMutableList()
        }

        // play game
        for (i in 1..20) {
            input.chunked(7).forEachIndexed { monkeyNum, monkeyLines ->
                monkeyItems[monkeyNum].forEach { itemWorry ->
                    monkeyInspected[monkeyNum] = monkeyInspected[monkeyNum] + 1
                    val initialWorry = calculateWorry(itemWorry, monkeyLines[2])
                    val worry = initialWorry / 3
                    val targetMonkey = calculateTargetMonkey(worry, monkeyLines[3], monkeyLines[4], monkeyLines[5])
                    monkeyItems[targetMonkey].add(worry)
                }
                monkeyItems[monkeyNum].clear()
            }
        }

        val sortedMonkeys = monkeyInspected.sortedDescending()
        return sortedMonkeys[0] * sortedMonkeys[1]
    }

    override fun part2(input: List<String>): Long {
        val numMonkeys = input.filter { it.startsWith("Monkey ") }.size
        val monkeyItems = List(numMonkeys) { mutableListOf<Long>() }.toMutableList()
        val monkeyInspected = List(numMonkeys) { 0L }.toMutableList()
        val allDivisors = mutableListOf<Long>()

        // initialise items and find lcm
        input.chunked(7).forEachIndexed { monkeyNum, monkeyLines ->
            monkeyItems[monkeyNum] = monkeyLines[1].trim()
                .substringAfter("Starting items: ")
                .split(",")
                .map { it.trim() }
                .map { it.toLong() }
                .toMutableList()
            val divisor = monkeyLines[3].split(" ").last().toLong()
            allDivisors.add(divisor)
        }

        val lcm = allDivisors.reduce { acc, bigInteger -> acc * bigInteger }

        // play game
        for (i in 1..10_000) {
            input.chunked(7).forEachIndexed { monkeyNum, monkeyLines ->
                monkeyItems[monkeyNum].forEach { itemWorry ->
                    monkeyInspected[monkeyNum] = monkeyInspected[monkeyNum] + 1
                    val initialWorry = calculateWorry(itemWorry, monkeyLines[2])
                    val worry = initialWorry % lcm
                    val targetMonkey = calculateTargetMonkey(worry, monkeyLines[3], monkeyLines[4], monkeyLines[5])
                    monkeyItems[targetMonkey].add(worry)
                }
                monkeyItems[monkeyNum].clear()
            }
        }

        val sortedMonkeys = monkeyInspected.sortedDescending()
        return sortedMonkeys[0] * sortedMonkeys[1]
    }

    private fun calculateWorry(itemWorry: Long, operationLine: String): Long {
        val operation = operationLine.substringAfter("  Operation: new = old ")
        if (operation == "* old") {
            return itemWorry * itemWorry
        } else {
            val (operator, operand) = operation.split(" ")
            return when (operator) {
                "+" -> itemWorry + operand.toInt()
                "*" -> itemWorry * operand.toInt()
                else -> throw IllegalStateException("Unknown operand in $operationLine")
            }
        }
    }

    private fun calculateTargetMonkey(worry: Long, testLine: String, trueLine: String, falseLine: String): Int {
        val testDivBy = testLine.split(" ").last().toLong()
        val trueTarget = trueLine.split(" ").last().toInt()
        val falseTarget = falseLine.split(" ").last().toInt()
        return if (worry % testDivBy == 0L) trueTarget else falseTarget
    }
}
