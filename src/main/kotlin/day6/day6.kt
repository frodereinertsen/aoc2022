package day6

fun findStartOfPacketMarkerPosition(input: String): Int? {
    return findPositionOfLastUniqueCharacterInWindow(input, 4)
}

fun findStartOfMessageMarkerPosition(input: String): Int? {
    return findPositionOfLastUniqueCharacterInWindow(input, 14)
}

private fun findPositionOfLastUniqueCharacterInWindow(input: String, windowSize: Int): Int? {
    input.withIndex().windowed(windowSize).forEach { fourChars ->
        if (fourChars.map { it.value }.toSet().size == windowSize) {
            return fourChars.last().index + 1
        }
    }
    return null
}