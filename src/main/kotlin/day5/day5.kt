package day5

data class Move(val times: Int, val fromIndex: Int, val toIndex: Int)

class Cargo(val stacks: List<Stack>, private val moves: List<Move>) {
    fun moveOneAtATime() {
        moves.forEach { move ->
            repeat(move.times) {
                stacks[move.toIndex].stack.addFirst(stacks[move.fromIndex].stack.removeFirst())
            }
        }
    }
    fun moveManyAtOnce() {
        moves.forEach { move ->
            stacks[move.fromIndex].stack.take(move.times).reversed().forEach { stacks[move.toIndex].stack.addFirst(it) }
                .also { repeat(move.times) { stacks[move.fromIndex].stack.removeFirst() } }
        }
    }
}

data class Stack(val inputPosition: Int, val stack: ArrayDeque<Char>)

fun moveCratesPart1(input: String): String {
    val cargo = createCargo(input)
    cargo.moveOneAtATime()
    return cargo.stacks.map { it.stack.first() }.joinToString("")
}

fun moveCratesPart2(input: String): String {
    val cargo = createCargo(input)
    cargo.moveManyAtOnce()
    return cargo.stacks.map { it.stack.first() }.joinToString("")
}

private fun createCargo(input: String): Cargo {
    val stackInput = input.lines().takeWhile { it.isNotEmpty() }.reversed()
    val stacks = mutableListOf<Stack>()
    stackInput.take(1).first().forEachIndexed { index, c ->
        if (c != ' ') {
            stacks.add(Stack(index, ArrayDeque()))
        }
    }
    stackInput.drop(1).forEach { chars ->
        stacks.forEach {
            if (chars.length >= it.inputPosition && chars[it.inputPosition] != ' ') {
                it.stack.addFirst(chars[it.inputPosition])
            }
        }
    }

    val movesInput = input.lines().dropWhile { it.isNotEmpty() }
    val moves = mutableListOf<Move>()
    movesInput.filter { it.isNotEmpty() }.map {
        moves.add(getMove(it))
    }

    return Cargo(stacks, moves)
}


val movesRegex = Regex("move (\\d+) from (\\d+) to (\\d+)")

fun getMove(line: String): Move {
    val numbers = movesRegex.find(line)?.groups?.toList()?.subList(1, 4)?.map { it?.value?.toInt()!! }
        ?: throw RuntimeException("yukk")
    return Move(numbers[0], numbers[1] - 1, numbers[2] - 1)
}