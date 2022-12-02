import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun part1Test() {
        val input = """
            A Y
            B X
            C Z
        """.trimIndent().split('\n')

        val answer = Day02().part1(input)
        assertEquals(15L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            A Y
            B X
            C Z
        """.trimIndent().split('\n')

        val answer = Day02().part2(input)
        assertEquals(12L, answer)
    }
}
