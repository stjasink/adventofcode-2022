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
        val fs = Dir("/", null)
        var dir = fs
        val smallDirs = mutableListOf<Dir>()

        input.drop(1).forEach { line ->
            when {
                line == "$ ls" -> {
                    // listing
                }
                line.startsWith("$ cd") -> {
                    val dirName = line.split(" ")[2]
                    if (dirName == "..") {
                        dir.calcSize()
                        if (dir.size!! <= 100000) {
                            smallDirs.add(dir)
                        }
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
        while (dir.name != "/") {
            dir.calcSize()
            dir = dir.parent!!
        }

        val total = smallDirs.sumOf { it.size!! }

        return total
    }

    override fun part2(input: List<String>): Long {
        val fs = Dir("/", null)
        var dir = fs
        val dirSizes = mutableListOf<Long>()

        input.drop(1).forEach { line ->
            when {
                line == "$ ls" -> {
                    // listing
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

        val spaceUsed = fs.size!!
        val spaceLeft = 70000000L - spaceUsed
        val spaceNeeded = 30000000L - spaceLeft

        dirSizes.sort()
        val delSize = dirSizes.first { it >= spaceNeeded }

        return delSize
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
