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
            row.mapIndexed { colNum, tree ->
                if (colNum == 0 || rowNum == 0 || colNum == row.size - 1 || rowNum == trees[0].size - 1) {
                    true
                } else {
                    val col = trees.map { row -> row[colNum] }
                    val leftTreesMax = row.subList(0, colNum).max()
                    val rightTreesMax = row.subList(colNum+1, trees[rowNum].size).max()
                    val topTreesMax = col.subList(0, rowNum).max()
                    val bottomTreesMax = col.subList(rowNum+1, col.size).max()
                    tree > leftTreesMax || tree > rightTreesMax || tree > topTreesMax || tree > bottomTreesMax
                }
            }.count { it }
        }.sum().toLong()
    }

    override fun part2(input: List<String>): Long {
        val trees = input.map { row-> row.map { it.digitToInt() } }
        return trees.mapIndexed { rowNum, row ->
            row.mapIndexed { colNum, tree ->
                val col = trees.map { row -> row[colNum] }
                val leftScore = row.subList(0, colNum).reversed().findScore(tree)
                val rightScore = row.subList(colNum+1, trees[rowNum].size).findScore(tree)
                val topScore = col.subList(0, rowNum).reversed().findScore(tree)
                val bottomScore = col.subList(rowNum+1, col.size).findScore(tree)
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
