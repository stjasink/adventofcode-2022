import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-10.txt")
    val solver = Day10()
    runAndTime(solver, input)
}

class Day10 : Solver {

    override fun part1(input: List<String>): Long {
        var xReg = 1L
        var cycleNum = 0L
        var total = 0L

        fun checkSignal() {
            if (cycleNum == 20L || (cycleNum - 20) % 40 == 0L) {
                val signal = xReg * cycleNum
                total += signal
            }
        }

        input.forEachIndexed { lineNum, line ->
            if (line == "noop") {
                cycleNum += 1
                checkSignal()
            } else if (line.startsWith("addx")) {
                val willAdd = line.substringAfter("addx ").toLong()
                cycleNum += 1
                checkSignal()
                cycleNum += 1
                checkSignal()
                xReg += willAdd
            }
        }
        return total
    }

    override fun part2(input: List<String>): Long {
        var xReg = 1L
        var cycleNum = 0L

        val screenLine = StringBuilder()

        fun drawPixel() {
            val drawingPos = cycleNum % 40
            val spritePos = xReg-1 .. xReg+1
            if (spritePos.contains(drawingPos)) {
                screenLine.append("#")
            } else {
                screenLine.append(".")
            }
            if (drawingPos == 39L) {
                println(screenLine)
                screenLine.clear()
            }
        }

        input.forEachIndexed { lineNum, line ->
            if (line == "noop") {
                drawPixel()
                cycleNum += 1
            } else if (line.startsWith("addx")) {
                val willAdd = line.substringAfter("addx ").toLong()
                drawPixel()
                cycleNum += 1
                drawPixel()
                cycleNum += 1
                xReg += willAdd
            }
        }

        return 0L
    }
}
