package day12

fun part1(input: List<String>): Int {
    val parsed = parseInput(input)
    val map = HeightMap(parsed)
    val graph = map.createGraphPart1()
    val shortestPathTree: Map<Coordinate, Coordinate?> = dijkstra(graph, map.start.first)

    val shortestPath = shortestPath(shortestPathTree, map.start.first, map.end.first)

    return shortestPath.count() - 1
}

fun part2(input: List<String>): Int {
    val parsed = parseInput(input)
    val map = HeightMap(parsed)
    val graph = map.createGraphPart1()
    val shortestPathTree: Map<Coordinate, Coordinate?> = dijkstra(graph, map.start.first)

    val shortestPath = shortestPath(shortestPathTree, map.start.first, map.end.first)

    return shortestPath.count() - 1
}

typealias Coordinate = Pair<Int, Int>
typealias Coordinates = Map<Coordinate, Char>

fun parseInput(input: List<String>): Coordinates = input.map { it.toCharArray() }.toTypedArray()
    .flatMapIndexed { rowIndex, line -> line.mapIndexed { columnIndex, char -> (rowIndex to columnIndex) to char } }
    .toMap()

fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
    val S: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance

    val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
    delta[start] = 0

    val previous: MutableMap<T, T?> = graph.vertices.associateWith { null }.toMutableMap()

    while (S != graph.vertices) {
        val v: T = delta
            .filter { !S.contains(it.key) }
            .minBy { it.value }!!
            .key

        graph.edges.getValue(v).minus(S).forEach { neighbor ->
            val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

            if (newPath < delta.getValue(neighbor)) {
                delta[neighbor] = newPath
                previous[neighbor] = v
            }
        }

        S.add(v)
    }

    return previous.toMap()
}

fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
    fun pathTo(start: T, end: T): List<T> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return pathTo(start, end)
}

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(): Set<T> = map { (a, b) -> listOf(a, b) }
    .flatten()
    .toSet()

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(predicate: (T) -> Boolean): Set<T> = map { (a, b) -> listOf(a, b) }
    .flatten()
    .filter(predicate)
    .toSet()

data class Graph<T>(
    val vertices: Set<T>,
    val edges: Map<T, Set<T>>,
    val weights: Map<Pair<T, T>, Int>
) {
    constructor(weights: Map<Pair<T, T>, Int>) : this(
        vertices = weights.keys.toList().getUniqueValuesFromPairs(),
        edges = weights.keys
            .groupBy { it.first }
            .mapValues { it.value.getUniqueValuesFromPairs { x -> x !== it.key } }
            .withDefault { emptySet() },
        weights = weights
    )
}

class HeightMap(val coordinates: Coordinates) {
    private val maxRowIndex = coordinates.keys.maxBy { it.first }.first
    private val maxColumnIndex = coordinates.keys.maxBy { it.second }.second

    fun createGraphPart1(): Graph<Pair<Int, Int>> {
        val weights = mutableMapOf<Pair<Coordinate, Coordinate>, Int>()

        for (rowIndex in 0..maxRowIndex) {
            for (columnIndex in 0..maxColumnIndex) {
                val coordinate = rowIndex to columnIndex
                val elevation = getSymbol(coordinate).elevation()
                listOfNotNull(
                    coordinate.left(),
                    coordinate.right(),
                    coordinate.over(),
                    coordinate.under()
                ).forEach { other ->
                    val otherElevation = getSymbol(other).elevation()
                    if (elevation + 1 >= otherElevation) {
                        weights[coordinate to other] = 1
                    }
                }
            }
        }

        return Graph(weights)
    }

    fun createGraphPart2(): Graph<Pair<Int, Int>> {
        val weights = mutableMapOf<Pair<Coordinate, Coordinate>, Int>()

        for (rowIndex in 0..maxRowIndex) {
            for (columnIndex in 0..maxColumnIndex) {
                val coordinate = rowIndex to columnIndex
                val elevation = getSymbol(coordinate).elevation()
                listOfNotNull(
                    coordinate.left(),
                    coordinate.right(),
                    coordinate.over(),
                    coordinate.under()
                ).forEach { other ->
                    val otherElevation = getSymbol(other).elevation()
                    if (elevation + 1 >= otherElevation) {
                        weights[coordinate to other] = 1
                    }
                }
            }
        }

        return Graph(weights)
    }

    private fun getSymbol(coordinate: Pair<Int, Int>) =
        coordinates[coordinate] ?: throw RuntimeException("could not find coordinate")

    val start by lazy {
        coordinates.firstNotNullOf { if (it.value == 'S') it else null }.toPair()
    }

    val end by lazy {
        coordinates.firstNotNullOf { if (it.value == 'E') it else null }.toPair()
    }

    private fun Coordinate.left(): Coordinate? {
        val (rowIndex, columnIndex) = this
        return if (rowIndex - 1 >= 0) {
            rowIndex - 1 to columnIndex
        } else null
    }

    private fun Coordinate.right(): Coordinate? {
        val (rowIndex, columnIndex) = this
        return if (rowIndex + 1 <= maxRowIndex) {
            rowIndex + 1 to columnIndex
        } else null
    }

    private fun Coordinate.over(): Coordinate? {
        val (rowIndex, columnIndex) = this
        return if (columnIndex - 1 >= 0) {
            rowIndex to columnIndex - 1
        } else null
    }

    private fun Coordinate.under(): Coordinate? {
        val (rowIndex, columnIndex) = this
        return if (columnIndex + 1 <= maxColumnIndex) {
            rowIndex to columnIndex + 1
        } else null
    }

    private fun Char.elevation(): Int = when (this) {
        'S' -> 'a'
        'E' -> 'z'
        else -> this
    }.code
}
