package day9

import kotlin.math.abs

fun part1(input: List<String>): Int {
    val board = move(input, 2)
    return board.tailPositions.toSet().size
}

fun part2(input: List<String>): Int {
    val board = move(input, 10)
    return board.tailPositions.toSet().size
}

private fun move(input: List<String>, numKnots: Int): Board {
    val board = Board(numKnots)
    for (line in input) {
        val (direction, steps) = line.split(" ").let { it[0] to it[1].toInt() }
        board.move(direction, steps)
    }
    return board
}


class Board(numKnots: Int) {
    private val knotPositions = (1..numKnots).map { mutableListOf(Position(0, 0)) }

    val tailPositions: List<Position>
        get() = knotPositions.last()

    fun move(direction: String, steps: Int) {
        repeat(steps) {
            with(knotPositions.first().last()) {
                val newHead = when (direction) {
                    "U" -> copy(vertical = vertical + 1)
                    "D" -> copy(vertical = vertical - 1)
                    "L" -> copy(horizontal = horizontal - 1)
                    "R" -> copy(horizontal = horizontal + 1)
                    else -> throw IllegalArgumentException()
                }
                knotPositions.first().add(newHead)
            }

            knotPositions.windowed(2, 1) {
                val positionsInFront = it[0]
                val positionsBehind = it[1]

                val newPosition = moveTheOneBehind(
                    positionsInFront.last(),
                    positionsBehind.last()
                )

                positionsBehind.add(newPosition)
            }
        }
    }

    private fun moveTheOneBehind(inFront: Position, behind: Position): Position = with(behind) {
        return when {
            distance(inFront.horizontal, horizontal) == 2 && distance(inFront.vertical, vertical) == 0 -> {
                copy(horizontal = horizontal + direction(inFront.horizontal, horizontal))
            }

            distance(inFront.vertical, vertical) == 2 && distance(inFront.horizontal, horizontal) == 0 -> {
                copy(vertical = vertical + direction(inFront.vertical, vertical))
            }

            distance(inFront.horizontal, horizontal) >= 2 && distance(inFront.vertical, vertical) >= 1 ||
                    distance(inFront.vertical, vertical) >= 2 && distance(inFront.horizontal, horizontal) >= 1 -> {
                copy(
                    horizontal = horizontal + direction(inFront.horizontal, horizontal),
                    vertical = vertical + direction(inFront.vertical, vertical)
                )
            }

            else -> copy()
        }
    }

    private fun distance(inFront: Int, behind: Int): Int = abs(inFront - behind)

    private fun direction(inFront: Int, behind: Int): Int = if (inFront > behind) 1 else if (inFront < behind) -1 else 0
}

data class Position(val vertical: Int, val horizontal: Int)
