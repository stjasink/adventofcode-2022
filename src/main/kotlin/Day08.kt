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
        val trees = input.map { row-> row.map { it.digitToInt() } }
        return trees.mapIndexed { rowNum, row ->
            row.mapIndexed { colNum, _ ->
                if (colNum == 0 || rowNum == 0) {
                    true
                } else if (colNum == row.size - 1 || rowNum == trees[0].size - 1) {
                    true
                } else {
                    val thisCol = trees.map { row -> row[colNum] }
                    val leftTrees = trees[rowNum].subList(0, colNum)
                    val rightTrees = trees[rowNum].subList(colNum+1, trees[rowNum].size)
                    val topTrees = thisCol.subList(0, rowNum)
                    val bottomTrees = thisCol.subList(rowNum+1, thisCol.size)
                    if (trees[rowNum][colNum] > leftTrees.max()) {
                        true
                    } else if (trees[rowNum][colNum] > rightTrees.max()) {
                        true
                    } else if (trees[rowNum][colNum] > topTrees.max()) {
                        true
                    } else if (trees[rowNum][colNum] > bottomTrees.max()) {
                        true
                    } else {
                        false
                    }
                }
            }.count { it }
        }.sum().toLong()
    }

    override fun part2(input: List<String>): Long {
        val trees = input.map { row-> row.map { it.digitToInt() } }
        return trees.mapIndexed { rowNum, row ->
            row.mapIndexed { colNum, tree ->
                val thisCol = trees.map { row -> row[colNum] }
                val leftScore = trees[rowNum].subList(0, colNum).reversed().findScore(tree)
                val rightScore = trees[rowNum].subList(colNum+1, trees[rowNum].size).findScore(tree)
                val topScore = thisCol.subList(0, rowNum).reversed().findScore(tree)
                val bottomScore = thisCol.subList(rowNum+1, thisCol.size).findScore(tree)
                leftScore * rightScore * topScore * bottomScore
            }.max()
        }.max()
    }

    private fun List<Int>.findScore(tree: Int): Long {
        var count = 0L
        forEach { otherTree ->
            if (otherTree < tree) {
                count += 1
            } else {
                return count + 1
            }
        }
        return count
    }
}
