package day2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import readLines

internal class Day2Test {
    private val exampleInput = """
        A Y
        B X
        C Z
    """.trimIndent()

    @Test
    fun part1() {
        with(RockPaperScissorsGame()) {
            part1(exampleInput.lines())
            assertEquals(15, yourScore)
        }
        with(RockPaperScissorsGame()) {
            part1(readLines("day2.txt"))
            assertEquals(12276, yourScore)
        }
    }

    @Test
    fun part2() {
        with(RockPaperScissorsGame()) {
            part2(exampleInput.lines())
            assertEquals(12, yourScore)
        }
        with(RockPaperScissorsGame()) {
            part2(readLines("day2.txt"))
            assertEquals(9975, yourScore)
        }
    }
}
