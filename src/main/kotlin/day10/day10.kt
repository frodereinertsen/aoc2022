package day10

fun part1(input: List<String>): Int {
    val cpu = CPU().apply { runInstructions(input) }

    return cpu.cycleValues.withIndex().sumOf { (index, value) ->
        val cycle = index + 1
        if (cycle == 20 || (cycle - 20) % 40 == 0) {
            cycle * value
        } else {
            0
        }
    }
}

fun part2(input: List<String>) {
    val cpu = CPU().apply { runInstructions(input) }

    cpu.cycleValues.forEachIndexed { index, spriteMiddlePosition ->
        val crtPosition = index % 40

        if (crtPosition in (spriteMiddlePosition - 1 .. spriteMiddlePosition + 1)) {
            print("#")
        } else {
            print(".")
        }

        if (crtPosition == 39) {
            println()
        }
    }
}

class CPU {
    private var x = 1
    val cycleValues = mutableListOf<Int>()

    fun runInstructions(input: List<String>) {
        for (line in input) {
            with(line) {
                when {
                    equals("noop") -> noop()
                    startsWith("addx") -> addX(line.split(" ")[1].toInt())
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    private fun addX(int: Int) {
        cycleValues.add(x)
        cycleValues.add(x)
        x += int
    }

    private fun noop() {
        cycleValues.add(x)
    }
}