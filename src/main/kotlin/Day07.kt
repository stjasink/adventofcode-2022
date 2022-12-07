import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-07.txt")
    val solver = Day07()
    runAndTime(solver, input)
}

class Day07 : Solver {

    override fun part1(input: List<String>): Long {
        val dirSizes = getDirSizes(input)
        return dirSizes.filter { it < 100000L }.sumOf { it }
    }

    override fun part2(input: List<String>): Long {
        val dirSizes = getDirSizes(input)
        val spaceUsed = dirSizes.max() // assume "/" dir is the max in the list
        val spaceLeft = 70000000L - spaceUsed
        val spaceNeeded = 30000000L - spaceLeft
        return dirSizes.sorted().first { it >= spaceNeeded }
    }

    private fun getDirSizes(input: List<String>): List<Long> {
        val fs = Dir("/", null)
        var dir = fs
        val dirSizes = mutableListOf<Long>()

        // drop line 1 as don't need to do "$ cd /" as we created the fs dir above
        input.drop(1).forEach { line ->
            when {
                line == "$ ls" -> {
                    // listing - no need to do anything here
                }
                line.startsWith("$ cd") -> {
                    val dirName = line.split(" ")[2]
                    if (dirName == "..") {
                        dir.calcSize()
                        dirSizes.add(dir.size!!)
                        dir = dir.parent!!
                    } else {
                        dir = dir.dirs.first { it.name == dirName }
                    }
                }

                line.startsWith("dir") -> {
                    val dirName = line.split(" ")[1]
                    dir.dirs.add(Dir(dirName, dir))
                }

                line[0].isDigit() -> {
                    val (size, name) = line.split(" ")
                    dir.files.add(File(name, size.toLong()))
                }
            }
        }
        while (true) {
            dir.calcSize()
            dirSizes.add(dir.size!!)
            if (dir.name == "/") {
                break
            }
            dir = dir.parent!!
        }

        return dirSizes
    }

    class Dir(
        val name: String,
        val parent: Dir?,
        var size: Long? = null,
        val dirs: MutableList<Dir> = mutableListOf(),
        val files: MutableList<File> = mutableListOf()
    ) {
        fun calcSize() {
            size = dirs.sumOf { it.size!! } + files.sumOf { it.size }
        }
    }

    class File(
        val name: String,
        val size: Long
    )
}
