fun chunkByElf(lines: List<String>): Sequence<List<Int>> = sequence {
    val currentElf = mutableListOf<Int>()
    for (line in lines) {
        if (line.isEmpty()) {
            yield(currentElf.toList())
            currentElf.clear()
        } else {
            currentElf += line.toInt()
        }
    }
    yield(currentElf)
}

fun findMaxSumOfCalories(lines: List<String>): Int = chunkByElf(lines).map { it.sum() }.max()

fun findSumOfCaloriesTopThree(lines: List<String>): Int =
    chunkByElf(lines).map { it.sum() }.sortedDescending().take(3).sum()