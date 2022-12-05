package day5

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import readFile

internal class Day5Test {
    private val exampleInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2        
""".trimIndent()

    @Test
    fun part1() {
        assertEquals("CMZ", moveCratesPart1(exampleInput))
        assertEquals("RTGWZTHLD", moveCratesPart1(readFile("day5.txt")))
    }

    @Test
    fun part2() {
        assertEquals("MCD", moveCratesPart2(exampleInput))
        assertEquals("STHGRZZFR", moveCratesPart2(readFile("day5.txt")))
    }
}