package day11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import readLines

internal class Day11Test {
    val exampleInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
""".trimIndent()

    @Test
    fun part1() {
        assertEquals(10605, part1(exampleInput.lines()))
        assertEquals(76728, part1(readLines("day11.txt")))
    }

    @Test
    fun part2() {
        assertEquals(2713310158, part2(exampleInput.lines()))
        assertEquals(21553910156, part2(readLines("day11.txt")))
    }
}