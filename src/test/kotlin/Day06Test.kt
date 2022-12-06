import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun part1Test() {
        val input = """bvwbjplbgvbhsrlpgdmjqwftvncz
        """.trimIndent().split('\n')

        val answer = Day06().part1(input)
        assertEquals(5L, answer)
    }

    @Test
    fun part2Test() {
        val input = """nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg
        """.trimIndent().split('\n')

        val answer = Day06().part2(input)
        assertEquals(29L, answer)
    }
}
