package day6

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import readFile

internal class Day6Test {

    @Test
    fun part1() {
        assertEquals(7, findStartOfPacketMarkerPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(1953, findStartOfPacketMarkerPosition(readFile("day6.txt")))
    }

    @Test
    fun part2() {
        assertEquals(19, findStartOfMessageMarkerPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(2301, findStartOfMessageMarkerPosition(readFile("day6.txt")))
    }
}