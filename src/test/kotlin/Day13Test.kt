import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {

    @Test
    fun part1Test() {
        val input = """
            [1,1,3,1,1]
            [1,1,5,1,1]

            [[1],[2,3,4]]
            [[1],4]

            [9]
            [[8,7,6]]

            [[4,4],4,4]
            [[4,4],4,4,4]

            [7,7,7,7]
            [7,7,7]

            []
            [3]

            [[[]]]
            [[]]

            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent().split('\n')

        val answer = Day13().part1(input)
        assertEquals(13L, answer)
    }

    @Test
    fun part1Test2() {
        val input = """
            [[],[6,[7,5]],[6,6,3]]
            [[],[[2,3],5,6],[7,[]]]
        """.trimIndent().split('\n')

        val answer = Day13().part1(input)
        assertEquals(0L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            [1,1,3,1,1]
            [1,1,5,1,1]

            [[1],[2,3,4]]
            [[1],4]

            [9]
            [[8,7,6]]

            [[4,4],4,4]
            [[4,4],4,4,4]

            [7,7,7,7]
            [7,7,7]

            []
            [3]

            [[[]]]
            [[]]

            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent().split('\n')

        val answer = Day13().part2(input)
        assertEquals(0L, answer)
    }
}
