import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-13.txt")
    val solver = Day13()
    runAndTime(solver, input)
}

class Day13 : Solver {

    override fun part1(input: List<String>): Long {
        var total = 0L

        input.chunked(3).forEachIndexed { index, pair ->
            val left = parseList(pair[0])
            val right = parseList(pair[1])
            val compare = compareLists(left, right)
            println(pair[0])
            println(pair[1])
            println(compare)
            if (compare == -1) {
                total += (index + 1)
            }
        }
        return total
    }

    fun parseList(input: String): List<ValueOrList> {
        if (input[0] != '[') {
            throw IllegalStateException("String $input does not start with [")
        }
        val list = mutableListOf<ValueOrList>()
        var currVal: Int? = null
        // drop the initial [
        print('[')
        input.drop(1).forEachIndexed { index, c ->
            print(c)
            when {
                c.isDigit() -> {
                    if (currVal == null) {
                        currVal = c.digitToInt()
                    } else {
                        currVal = currVal!! * 10
                        currVal = currVal!! + c.digitToInt()
                    }
                }
                c == ',' -> {
                    if (currVal != null) {
                        list.add(ValueOrList(value = currVal!!))
                        currVal = null
                    } else {
                        println("currval was null at comma")
                    }
                }
                c == '[' -> {
                    if (currVal != null) {
                        println("currval was not null at [ !!")
                    }
                    val restOfInput = input.substring(index + 1)
                    val subList = parseList(restOfInput)
                    list.add(ValueOrList(list = subList))
                }
                c == ']' -> {
                    if (currVal != null) {
                        list.add(ValueOrList(value = currVal!!))
                        currVal = null
                    }
                    return list
                }
                else -> {
                    throw IllegalStateException("Unexpected input $c")
                }
            }
        }
        throw IllegalStateException("Unbalanced brackets in $input")
    }

    fun compareLists(left: List<ValueOrList>, right: List<ValueOrList>): Int {

        left.forEachIndexed { index, leftVal ->
            if (right.size <= index) {
                // right side ran out of values first
                return 1
            }
            val rightVal = right[index]
            if (leftVal.value != null && rightVal.value != null) {
                val comp = leftVal.value.compareTo(rightVal.value)
                if (comp != 0) return comp
            } else if (leftVal.list != null && rightVal.list != null) {
                val comp = compareLists(leftVal.list, rightVal.list)
                if (comp != 0) return comp
            } else if (leftVal.value != null && rightVal.list != null) {
                val comp = compareLists(listOf(leftVal), rightVal.list)
                if (comp != 0) return comp
            } else if (leftVal.list != null && rightVal.value != null) {
                val comp = compareLists(leftVal.list, listOf(rightVal))
                if (comp != 0) return comp
            } else {
                throw IllegalStateException("Could not work out what to compare")
            }
        }

        // left side ran out of values first
        return -1
    }



    data class ValueOrList(
        val value: Int? = null,
        val list: List<ValueOrList>? = null
    )


    override fun part2(input: List<String>): Long {
        return 0L
    }
}
