import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-08.txt")
    val solver = Day08()
    runAndTime(solver, input)
}

class Day08 : Solver {

    override fun part1(input: List<String>): Long {
        val trees = mutableListOf<MutableList<Int>>()
        val visible = mutableListOf<MutableList<Boolean>>()

        input.forEach { row ->
            trees.add(row.toCharArray().map { it.digitToInt() }.toMutableList())
            visible.add(Array(row.length) {false} .toMutableList())
        }

        trees.forEachIndexed { rowNum, row ->
            row.forEachIndexed { colNum, _ ->
                if (colNum == 0 || rowNum == 0) {
                    visible[rowNum][colNum] = true
                } else if (colNum == row.size - 1 || rowNum == trees[0].size - 1) {
                    visible[rowNum][colNum] = true
                } else {
                    val leftTrees = trees[rowNum].subList(0, colNum)
                    val rightTrees = trees[rowNum].subList(colNum+1, trees[rowNum].size)
                    val thisCol = trees.map { row -> row[colNum] }
                    val topTrees = thisCol.subList(0, rowNum)
                    val bottomTrees = thisCol.subList(rowNum+1, thisCol.size)
                    if (trees[rowNum][colNum] > leftTrees.max()) {
                        visible[rowNum][colNum] = true
                    } else if (trees[rowNum][colNum] > rightTrees.max()) {
                        visible[rowNum][colNum] = true
                    } else if (trees[rowNum][colNum] > topTrees.max()) {
                        visible[rowNum][colNum] = true
                    } else if (trees[rowNum][colNum] > bottomTrees.max()) {
                        visible[rowNum][colNum] = true
                    }
                }
            }
        }

        val total = visible.sumOf { row ->
            row.count { it }
        }.toLong()

        return total
    }

    override fun part2(input: List<String>): Long {
        val trees = mutableListOf<MutableList<Int>>()
        val scores = mutableListOf<MutableList<Long>>()

        input.forEach { row ->
            trees.add(row.toCharArray().map { it.digitToInt() }.toMutableList())
            scores.add(Array(row.length) {0L}.toMutableList())
        }

        trees.forEachIndexed { rowNum, row ->
            row.forEachIndexed { colNum, tree ->
                val leftTrees = trees[rowNum].subList(0, colNum).reversed()
                val rightTrees = trees[rowNum].subList(colNum+1, trees[rowNum].size)
                val thisCol = trees.map { row -> row[colNum] }
                val topTrees = thisCol.subList(0, rowNum).reversed()
                val bottomTrees = thisCol.subList(rowNum+1, thisCol.size)
                val leftScore = findScore(tree, leftTrees)
                val rightScore = findScore(tree, rightTrees)
                val bottomScore = findScore(tree, bottomTrees)
                val topScore = findScore(tree, topTrees)
                scores[rowNum][colNum] = leftScore * rightScore * topScore * bottomScore
            }
        }

        val total = scores.maxOf { row ->
            row.max()
        }

        return total
    }

    private fun findScore(tree: Int, list: List<Int>): Long {
        var count = 0L
        list.forEach { otherTree ->
            if (otherTree < tree) {
                count += 1
            } else {
                return count + 1
            }
        }
        return count
    }
}
