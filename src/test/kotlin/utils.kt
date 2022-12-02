object A

fun readLines(fileName: String): List<String> =
    A::class.java.classLoader.getResource("${fileName}").readText().lines().dropLastWhile { it.isEmpty() }