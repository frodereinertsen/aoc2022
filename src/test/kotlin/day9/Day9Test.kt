package day9

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import readLines

internal class Day9Test {

    private val example1 = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    private val example2 = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(13, part1(example1.lines()))
        assertEquals(6376, part1(readLines("day9.txt")))
    }

    @Test
    fun part2() {
        assertEquals(1, part2(example1.lines()))
        assertEquals(36, part2(example2.lines()))
        assertEquals(2607, part2(readLines("day9.txt")))
    }
}