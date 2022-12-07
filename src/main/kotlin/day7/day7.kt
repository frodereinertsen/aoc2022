package day7

fun deleteDirToFreeUpSpace(input: String): Long {
    val total = 70000000
    val needed = 30000000
    val rootDirectory = parseDirectories(input)
    val used = rootDirectory.size
    val unused = total - used
    val minToRemove = needed - unused
    return rootDirectory.allDirectoriesRecursive.filter { it.size > minToRemove }.minBy { it.size }.size
}

fun sizeOfDirsUnderLimit(input: String): Long {
    val rootDirectory = parseDirectories(input)
    return rootDirectory.allDirectoriesRecursive.filter { it.size <= 100000 }.sumOf { it.size }
}

fun parseDirectories(input: String): Directory {

    val rootDirectory = Directory("/")
    var currentDirectory = rootDirectory
    val lineIterator = input.lines().iterator()

    while (lineIterator.hasNext()) {
        with(lineIterator.next()) {
            when {
                equals("$ cd /") ->
                    currentDirectory = rootDirectory

                equals("$ cd ..") ->
                    currentDirectory = currentDirectory.parentDirectory
                        ?: throw IllegalStateException("no parent")

                startsWith("$ cd") ->
                    currentDirectory = currentDirectory.directories.find { it.name == substring(5) }
                        ?: throw IllegalStateException("no such directory")

                startsWith("dir") ->
                    currentDirectory.addDirectory(name = substring(4))

                isNotEmpty() && get(0).isDigit() ->
                    currentDirectory.addFile(split(" ")[1], split(" ")[0].toLong())

                else -> {}
            }
        }
    }

    return rootDirectory
}

data class File(val name: String, val size: Long)
data class Directory(
    val name: String,
    val files: MutableList<File> = mutableListOf(),
    val directories: MutableList<Directory> = mutableListOf(),
    val parentDirectory: Directory? = null
) {
    val size: Long by lazy {
        files.sumOf { it.size } + directories.sumOf { it.size }
    }

    val allDirectoriesRecursive: List<Directory>
        get() = directories + directories.flatMap { it.allDirectoriesRecursive }

    fun addDirectory(name: String) {
        directories.add(Directory(name = name, parentDirectory = this))
    }

    fun addFile(name: String, size: Long) {
        files.add(File(name, size))
    }

    override fun toString(): String {
        return "Directory(name='$name', files=$files, directories=$directories, parentDirectory=${parentDirectory?.name})"
    }
}