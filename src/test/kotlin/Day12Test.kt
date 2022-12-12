import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class Day12Test {

    @Test
    fun part1Test() {
        val input = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent().split('\n')

        val answer = Day12().part1(input)
        assertEquals(31L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent().split('\n')

        val answer = Day12().part2(input)
        assertEquals(29L, answer)
    }


}
