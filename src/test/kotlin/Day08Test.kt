import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {

    @Test
    fun part1Test() {
        val input = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent().split('\n')

        val answer = Day08().part1(input)
        assertEquals(21L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent().split('\n')

        val answer = Day08().part2(input)
        assertEquals(8L, answer)
    }
}
