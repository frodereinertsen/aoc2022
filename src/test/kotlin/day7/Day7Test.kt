package day7

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import readFile

internal class Day7Test {

    private val exampleInput = """
    ${'$'} cd /
    ${'$'} ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    ${'$'} cd a
    ${'$'} ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    ${'$'} cd e
    ${'$'} ls
    584 i
    ${'$'} cd ..
    ${'$'} cd ..
    ${'$'} cd d
    ${'$'} ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
""".trimIndent()

    @Test
    fun part1() {
        assertEquals(95437, sizeOfDirsUnderLimit(exampleInput))
        assertEquals(1723892, sizeOfDirsUnderLimit(readFile("day7.txt")))
    }

    @Test
    fun part2() {
        assertEquals(24933642, deleteDirToFreeUpSpace(exampleInput))
        assertEquals(8474158, deleteDirToFreeUpSpace(readFile("day7.txt")))
    }
}