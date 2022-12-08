package day8

fun part1(input: String) = parse(input).countVisible()
fun part2(input: String) = parse(input).highestScenicScore()

class HeightMap(private val map: Array<IntArray>) {

    private val numRows = map.size
    private val numColumns = map.first().size

    fun countVisible() = allCoordinates().map(::isVisibleAt).count { isVisible -> isVisible }

    fun highestScenicScore() = allCoordinates().maxOfOrNull(::scenicScoreAt)

    private fun allCoordinates(): List<Coordinate> {
        val rowIndices = map.indices
        val columnIndices = map.first().indices
        return rowIndices.cartesianProduct(columnIndices).map { (row, column) -> Coordinate(row, column) }
    }

    private fun isVisibleAt(coordinate: Coordinate): Boolean {
        if (isEdge(coordinate)) {
            return true
        }

        return allDirectionsFrom(coordinate)
            .any { direction -> direction.all { other -> heightAt(other) < heightAt(coordinate) } }
    }

    private fun scenicScoreAt(coordinate: Coordinate): Int {
        if (isEdge(coordinate)) {
            return 0
        }

        return allDirectionsFrom(coordinate)
            .map { direction ->
                direction
                    .takeWhile { other -> !isEdge(other) && heightAt(other) < heightAt(coordinate) }
                    .count()
            }
            .fold(1) { product, count -> (count + 1) * product }
    }

    private fun isEdge(coordinate: Coordinate): Boolean {
        val (row, column) = coordinate

        return row == 0 ||
                row == numRows - 1 ||
                column == 0 ||
                column == numColumns - 1
    }

    private fun allDirectionsFrom(coordinate: Coordinate) = listOf(
        over(coordinate),
        leftOf(coordinate),
        rightOf(coordinate),
        under(coordinate),
    )

    private fun leftOf(coordinate: Coordinate) =
        (coordinate.column - 1 downTo 0).map { Coordinate(coordinate.row, it) }

    private fun rightOf(coordinate: Coordinate) =
        (coordinate.column + 1 until numColumns).map { Coordinate(coordinate.row, it) }

    private fun over(coordinate: Coordinate) =
        (coordinate.row - 1 downTo 0).map { Coordinate(it, coordinate.column) }

    private fun under(coordinate: Coordinate) =
        (coordinate.row + 1 until numRows).map { Coordinate(it, coordinate.column) }

    private fun heightAt(coordinate: Coordinate) = map[coordinate.row][coordinate.column]
}

data class Coordinate(val row: Int, val column: Int)

private fun <S, T> Iterable<S>.cartesianProduct(other: Iterable<T>) = flatMap { first ->
    other.map { second ->
        first to second
    }
}

private fun parse(input: String) =
    HeightMap(
        input.lines()
            .filter { it.isNotBlank() }
            .map { row -> row.map { it.digitToInt() }.toIntArray() }
            .toTypedArray()
    )