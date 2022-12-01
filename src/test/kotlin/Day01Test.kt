import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun part1Test() {
        val input = """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
            
        """.trimIndent().split('\n')

        val answer = Day1().part1(input)
        assertEquals(24000L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
                        1000
                        2000
                        3000

                        4000

                        5000
                        6000

                        7000
                        8000
                        9000

                        10000
                        
        """.trimIndent().split('\n')

        val answer = Day1().part2(input)
        assertEquals(45000L, answer)
    }
}
