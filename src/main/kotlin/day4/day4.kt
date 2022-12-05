package day4

fun countPairWithOneFullyContainedInAnother(pairs: List<String>): Int = pairs.count { pair ->
    val matches = getNumbers(pair)
    val range1 = (matches[0] .. matches[1]).toSet()
    val range2 = (matches[2] .. matches[3]).toSet()
    range1.containsAll(range2) || range2.containsAll(range1)
}

fun countPairsThatOverlap(pairs: List<String>): Int = pairs.count { pair ->
    val matches = getNumbers(pair)
    val range1 = (matches[0] .. matches[1]).toSet()
    val range2 = (matches[2] .. matches[3]).toSet()
    (range1 intersect range2).isNotEmpty()
}

val numbersRegex = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")

fun getNumbers(pair: String): List<Int> =
    numbersRegex.find(pair)?.groups?.toList()?.subList(1, 5)?.map { it?.value?.toInt()!! } ?: throw RuntimeException("yukk")
