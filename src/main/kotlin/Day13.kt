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
        return input.chunked(3).mapIndexed { index, pair ->
            val compare = compareStringLists(pair[0], pair[1])
            if (compare == -1) {
                index + 1L
            } else {
                0L
            }
        }.sum()
    }

    override fun part2(input: List<String>): Long {
        val dividers = listOf("[[2]]", "[[6]]")
        val allPackets = input.filter { it.isNotBlank() } + dividers
        val sortedPackets = allPackets.sortedWith(PacketComparator())
        val div1Pos = sortedPackets.indexOf(dividers[0]) + 1L
        val div2Pos = sortedPackets.indexOf(dividers[1]) + 1L
        return div1Pos * div2Pos
    }

    companion object {
        fun parseList(input: String): List<ValueOrList> {
            if (input[0] != '[') {
                throw IllegalStateException("String $input does not start with [")
            }
            val lists = mutableListOf<MutableList<ValueOrList>>()
            var currVal: Int? = null
            input.forEachIndexed { index, c ->
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
                            lists.last().add(ValueOrList(value = currVal!!))
                            currVal = null
                        }
                    }
                    c == '[' -> {
                        if (currVal != null) {
                            println("currval was not null at [ !!")
                        }
                        lists.add(mutableListOf())
                    }
                    c == ']' -> {
                        if (currVal != null) {
                            lists.last().add(ValueOrList(value = currVal!!))
                            currVal = null
                        }
                        if (lists.size == 1) {
                            return lists.first()
                        } else {
                            val subList = lists.removeLast()
                            lists.last().add(ValueOrList(list = subList))
                        }
                    }
                    else -> {
                        throw IllegalStateException("Unexpected input $c")
                    }
                }
            }
            throw IllegalStateException("Unbalanced brackets in $input")
        }


        fun compareStringLists(leftString: String, rightString: String): Int {
            val left = parseList(leftString)
            val right = parseList(rightString)
            return compareLists(left, right)
        }

        fun compareLists(left: List<ValueOrList>, right: List<ValueOrList>): Int {
            if (left.isEmpty() && right.isEmpty()) {
                return 0
            }

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
    }

    data class ValueOrList(
        val value: Int? = null,
        val list: List<ValueOrList>? = null
    )

    class PacketComparator: Comparator<String> {
        override fun compare(o1: String, o2: String): Int {
            return compareStringLists(o1, o2)
        }
    }
}
