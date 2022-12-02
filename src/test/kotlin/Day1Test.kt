import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Test {
    private val exampleInput = """
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
""".trimIndent()

    @Test
    fun puzzle1() {
        assertEquals(24000, findMaxSumOfCalories(exampleInput.lines()))
        assertEquals(74394, findMaxSumOfCalories(readLines("day1.txt")))
    }

    @Test
    fun puzzle2() {
        assertEquals(45000, findSumOfCaloriesTopThree(exampleInput.lines()))
        assertEquals(212836, findSumOfCaloriesTopThree(readLines("day1.txt")))
    }
}