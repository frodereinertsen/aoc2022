object A

fun readLines(fileName: String): List<String> =
    readFile(fileName).lines().dropLastWhile { it.isEmpty() }

fun readFile(fileName: String): String =
    A::class.java.classLoader.getResource(fileName).readText()