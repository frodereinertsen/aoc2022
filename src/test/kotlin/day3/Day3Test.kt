package day3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import readLines

internal class Day3Test {

    val exampleInput = """
    vJrwpWtwJgWrhcsFMMfFFhFp
    jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
    PmmdzqPrVvPwwTWBwg
    wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
    ttgJtRGJQctTZtZT
    CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()

    @Test
    fun part1() {
        assertEquals(157, sumOfItemsThatAreInBothHalfs(exampleInput.lines()))
        assertEquals(7817, sumOfItemsThatAreInBothHalfs(readLines("day3.txt")))
    }

    @Test
    fun part2() {
        assertEquals(70, sumOfCommonsInAllGroups(exampleInput.lines()))
        assertEquals(2444, sumOfCommonsInAllGroups(readLines("day3.txt")))
    }
}