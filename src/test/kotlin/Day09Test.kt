import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {

    @Test
    fun part1Test() {
        val input = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent().split('\n')

        val answer = Day09().part1(input)
        assertEquals(13L, answer)
    }

    @Test
    fun part2Test1() {
        val input = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent().split('\n')

        val answer = Day09().part2(input)
        assertEquals(1L, answer)
    }


    @Test
    fun part2Test2() {
        val input = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent().split('\n')

        val answer = Day09().part2(input)
        assertEquals(36L, answer)
    }
}
