package day12

fun main() {
    val nodes = 6
    val edges: Array<IntArray> = Array(nodes) { IntArray(nodes) { -1 } }
    edges[0][1] = 34
    edges[0][3] = 43
    edges[1][2] = 56
    edges[1][3] = 20
    edges[1][4] = 57
    edges[2][5] = 20
    edges[3][1] = 20
    edges[3][4] = 50
    edges[4][2] = 37

    dijkstra(0, edges, nodes)
}

fun dijkstra(source: Int, edges: Array<IntArray>, nodes: Int) {
    // Initialize single source
    val d = IntArray(nodes) { Integer.MAX_VALUE }
    val pi = IntArray(nodes) { -1 }
    d[source] = 0

    val S: MutableList<Int> = ArrayList()
    val Q: MutableList<Int> = (0 until nodes).toMutableList()

    // Iterations
    while (Q.isNotEmpty()) {
        val u: Int = extractMin(Q, d)
        S.add(u)

        edges[u].forEachIndexed { v, vd ->
            if (vd != -1 && d[v] > d[u] + vd) {
                d[v] = d[u] + vd
                pi[v] = u
            }
        }
    }

    println("d: ${d.contentToString()}")
    println("pi: ${pi.contentToString()}")
}

fun extractMin(Q: MutableList<Int>, d: IntArray): Int {
    var minNode = Q[0]
    var minDistance: Int = d[0]

    Q.forEach {
        if (d[it] < minDistance) {
            minNode = it
            minDistance = d[it]
        }
    }

    Q.remove(minNode)
    return minNode
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

fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
    val S: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance

    val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
    delta[start] = 0

    val previous: MutableMap<T, T?> = graph.vertices.map { it to null }.toMap().toMutableMap()

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

val input = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
""".trimIndent()

typealias Coordinate = Pair<Int, Int>
typealias Coordinates = Map<Coordinate, Char>
typealias CoordinateValue = Pair<Coordinate, Char>


fun parseInput(input: List<String>): Coordinates = input.map { it.toCharArray() }.toTypedArray()
    .flatMapIndexed { rowIndex, line -> line.mapIndexed { columnIndex, char -> (rowIndex to columnIndex) to char } }
    .toMap()

class HeightMap(private val coordinates: Coordinates) {
    private val maxRowIndex = coordinates.keys.maxBy { it.first }.first
    private val maxColumnIndex = coordinates.keys.maxBy { it.second }.second

    fun createGraph() {
        val weights = mutableSetOf<Pair<Pair<Char, Char>, Int>>()

        for (rowIndex in 0 ..maxRowIndex) {
            for (columnIndex in 0 ..maxColumnIndex) {
                val coordinate = rowIndex to columnIndex
                val elevation = getSymbol(coordinate).elevation()
                coordinate.left()?.let {
                    val otherElevation = getSymbol(it).elevation()
                    if (elevation in otherElevation .. otherElevation + 1 ) {
                        weights.add(getSymbol(coordinate) to getSymbol(it) to 1)
                    }
                }
                coordinate.right()?.let {

                }
                coordinate.over()?.let {

                }
                coordinate.under()?.let {

                }
            }
        }
    }

    private fun getSymbol(coordinate: Pair<Int, Int>) =
        coordinates[coordinate] ?: throw RuntimeException("could not find coordinate")

    private val start by lazy {
        coordinates.firstNotNullOf { if (it.value == 'S') it else null }.toPair()
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
            rowIndex - 1 to columnIndex
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

    private fun Char.elevation(): Int = when(this) {
        'S' -> 'a'
        'E' -> 'z'
        else -> this
    }.digitToInt()
}
