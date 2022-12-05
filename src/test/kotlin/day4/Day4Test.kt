package day4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import readLines

internal class Day4Test {
    val exampleInput = """
    2-4,6-8
    2-3,4-5
    5-7,7-9
    2-8,3-7
    6-6,4-6
    2-6,4-8
""".trimIndent()

    @Test
    fun part1() {
        assertEquals(2, countPairWithOneFullyContainedInAnother(exampleInput.lines()))
        assertEquals(569, countPairWithOneFullyContainedInAnother(readLines("day4.txt")))
    }

    @Test
    fun part2() {
        assertEquals(4, countPairsThatOverlap(exampleInput.lines()))
        assertEquals(936, countPairsThatOverlap(readLines("day4.txt")))
    }
}