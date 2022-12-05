package day3

fun sumOfItemsThatAreInBothHalfs(sacks: List<String>): Int = sacks.map { sack ->
    sack.firstHalf() intersect sack.lastHalf()
}.flatten().sumOf { it.priority }

fun sumOfCommonsInAllGroups(sacks: List<String>): Int {
    return sacks.map { it.toSet() }.windowed(3, 3) {
        it.reduce { intersection, sack ->
            intersection intersect sack
        }
    }.flatten().sumOf { it.priority }
}

private fun String.firstHalf(): Set<Char> = take(this.length / 2).toSet()
private fun String.lastHalf(): Set<Char> = takeLast(this.length / 2).toSet()

private val Char.priority
    get() = code - if (isUpperCase()) 38 else 96
