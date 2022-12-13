package day12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import readLines

internal class Day12Test {
    @Test
    fun shouldCalculateCorrectShortestPaths() {
        val weights: Map<Pair<String, String>, Int> = mapOf(
            Pair("A", "B") to 2,
            Pair("A", "C") to 8,
            Pair("A", "D") to 5,
            Pair("B", "C") to 1,
            Pair("C", "E") to 3,
            Pair("D", "E") to 2
        )

        val start = "A"
        val shortestPathTree = dijkstra(Graph(weights), start)

        assertEquals(listOf(start, "B", "C"), shortestPath(shortestPathTree, start, "C"))
        assertEquals(listOf(start, "B", "C", "E"), shortestPath(shortestPathTree, start, "E"))
        assertEquals(listOf(start, "D"), shortestPath(shortestPathTree, start, "D"))
    }

    val input = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(31, part1(input.lines()))
        assertEquals(481, part1(readLines("day12.txt")))
    }

    @Test
    fun part2() {
        assertEquals(31, part2(input.lines()))
        assertEquals(31, part2(readLines("day12.txt")))
    }
}