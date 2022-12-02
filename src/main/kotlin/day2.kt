import java.lang.IllegalArgumentException

sealed class Shape(
    val score: Int,
) {
    abstract fun beats(other: Shape): Boolean
    abstract fun loses(other: Shape): Boolean
    abstract fun getShapeForDesiredResult(result: Result): Shape

    companion object {
        fun fromSymbol(symbol: Char): Shape {
            return when (symbol) {
                in listOf('A', 'X') -> Rock
                in listOf('B', 'Y') -> Paper
                in listOf('C', 'Z') -> Scissors
                else -> throw RuntimeException("yikes")
            }
        }
    }

    object Rock : Shape(1) {
        override fun beats(other: Shape) = other == getShapeForDesiredResult(Result.WIN)
        override fun loses(other: Shape) = other == getShapeForDesiredResult(Result.LOSE)
        override fun getShapeForDesiredResult(result: Result) = when (result) {
            Result.WIN -> Scissors
            Result.LOSE -> Paper
            Result.DRAW -> Rock
        }
    }

    object Paper : Shape(2) {
        override fun beats(other: Shape) = other == getShapeForDesiredResult(Result.WIN)
        override fun loses(other: Shape) = other == getShapeForDesiredResult(Result.LOSE)
        override fun getShapeForDesiredResult(result: Result) = when (result) {
            Result.WIN -> Rock
            Result.LOSE -> Scissors
            Result.DRAW -> Paper
        }
    }

    object Scissors : Shape(3) {
        override fun beats(other: Shape) = other == getShapeForDesiredResult(Result.WIN)
        override fun loses(other: Shape) = other == getShapeForDesiredResult(Result.LOSE)
        override fun getShapeForDesiredResult(result: Result) = when (result) {
            Result.WIN -> Paper
            Result.LOSE -> Rock
            Result.DRAW -> Scissors
        }
    }
}

enum class Result {
    WIN, LOSE, DRAW;

    fun opposite(): Result {
        return when (this) {
            WIN -> LOSE
            LOSE -> WIN
            DRAW -> DRAW
        }
    }

    companion object {
        fun fromSymbol(symbol: Char): Result {
            return when (symbol) {
                'X' -> LOSE
                'Y' -> DRAW
                'Z' -> WIN
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class RockPaperScissorsGame {
    private val winScore = 6
    private val drawScore = 3

    var opponentScore = 0
    var yourScore = 0

    fun part1(rounds: List<String>) {
        for (round in rounds) {
            val opponentShape = Shape.fromSymbol(round[0])
            val yourShape = Shape.fromSymbol(round[2])
            play(opponentShape, yourShape)
        }
    }

    fun part2(rounds: List<String>) {
        for (round in rounds) {
            val opponentShape = Shape.fromSymbol(round[0])
            val yourShape = opponentShape.getShapeForDesiredResult(Result.fromSymbol(round[2]).opposite())
            play(opponentShape, yourShape)
        }
    }

    private fun play(opponentShape: Shape, yourShape: Shape) {
        opponentScore += opponentShape.score
        yourScore += yourShape.score
        when {
            opponentShape.beats(yourShape) -> opponentScore += winScore
            opponentShape.loses(yourShape) -> yourScore += winScore
            else -> {
                opponentScore += drawScore
                yourScore += drawScore
            }
        }
    }
}