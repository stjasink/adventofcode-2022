import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun part1Test() {
        val input = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent().split('\n')

        val answer = Day04().part1(input)
        assertEquals(2L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent().split('\n')

        val answer = Day04().part2(input)
        assertEquals(4L, answer)
    }
}
