package day8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFile

internal class Day8Test {

    private val exampleInput = """
    30373
    25512
    65332
    33549
    35390
""".trimIndent()

    @Test
    fun part1() {
        assertEquals(21, part1(exampleInput))
        assertEquals(1814, part1(readFile("day8.txt")))
    }

    @Test
    fun part2() {
        assertEquals(8, part2(exampleInput))
        assertEquals(330786, part2(readFile("day8.txt")))
    }
}