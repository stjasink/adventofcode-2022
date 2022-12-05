import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
    fun part1Test() {
        val input = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
        """.trimIndent().split('\n')

        val answer = Day05().part1(input)
        assertEquals("CMZ", answer)
    }

    @Test
    fun part2Test() {
        val input = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
        """.trimIndent().split('\n')

        val answer = Day05().part2(input)
        assertEquals("MCD", answer)
    }
}
