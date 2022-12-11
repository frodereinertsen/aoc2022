package day11

import kotlin.math.floor

fun part1(input: List<String>): Long {
    val monkeys = makeMonkeys(input).associateBy { it.number }
    repeat(20) {
        monkeys.values.forEach {
            it.inspect { (toMonkey, item) ->
                monkeys[toMonkey]?.items?.add(item)
            }
        }
    }

    return monkeys.values.sortedBy { it.inspections }.takeLast(2).fold(1L) { acc, monkey -> acc * monkey.inspections }
}

fun part2(input: List<String>, rounds: Int = 10000): Long {
    val monkeys = makeMonkeys(input).associateBy { it.number }
    val commonDivisibleBy = monkeys.values.map { it.divisibleBy }.fold(1) { product, div -> product * div }
    repeat(rounds) {
        monkeys.values.forEach {
            it.inspectPart2(commonDivisibleBy) { (toMonkey, item) ->
                monkeys[toMonkey]?.items?.add(item)
            }
        }
    }

    return monkeys.values.sortedBy { it.inspections }.takeLast(2).fold(1L) { acc, monkey -> acc * monkey.inspections }
}

data class Monkey(
    val number: Int,
    val items: MutableList<Long>,
    val operation: String,
    val divisibleBy: Int,
    val ifTrueThrowTo: Int,
    val ifFalseThrowTo: Int
) {
    var inspections: Int = 0

    fun inspect(moveItem: (item: Pair<Int, Long>) -> Unit) {
        while (items.isNotEmpty()) {
            inspections++
            var item = items.removeFirst()
            item = performOperation(item)
            item = floor(item.toDouble() / 3).toLong()
            if (item % divisibleBy == 0L) {
                moveItem(ifTrueThrowTo to item)
            } else {
                moveItem(ifFalseThrowTo to item)
            }
        }
    }

    fun inspectPart2(commonDivisibleBy: Int, moveItem: (item: Pair<Int, Long>) -> Unit) {
        while (items.isNotEmpty()) {
            inspections++
            var item = items.removeFirst()
            item = performOperation(item)
            item = item % commonDivisibleBy
            if (item % divisibleBy == 0L) {
                moveItem(ifTrueThrowTo to item)
            } else {
                moveItem(ifFalseThrowTo to item)
            }
        }
    }

    private fun performOperation(old: Long): Long {
        return if (operation.contains("*")) {
            val (first, second) = operation.split(" * ").let { getValue(it[0].trim(), old) to getValue(it[1].trim(), old) }
            first * second
        } else {
            val (first, second) = operation.split(" + ").let { getValue(it[0].trim(), old) to getValue(it[1].trim(), old) }
            first + second
        }
    }

    private fun getValue(s: String, old: Long): Long = when (s) {
        "old" -> old
        else -> s.toLong()
    }

    class Builder {
        var number: Int? = null
        var items: MutableList<Long>? = null
        var operation: String? = null
        var divisibleBy: Int? = null
        var ifTrueThrowTo: Int? = null
        var ifFalseThrowTo: Int? = null

        fun number(number: Int) = apply { this.number = number }
        fun items(items: MutableList<Long>) = apply { this.items = items }
        fun ifTrueThrowTo(number: Int) = apply { this.ifTrueThrowTo = number }
        fun ifFalseThrowTo(number: Int) = apply { this.ifFalseThrowTo = number }
        fun operation(operation: String) = apply { this.operation = operation }
        fun divisibleBy(divisibleBy: Int) = apply { this.divisibleBy = divisibleBy }

        fun build() = Monkey(this)
    }

    constructor(builder: Builder) : this(
        builder.number!!,
        builder.items!!,
        builder.operation!!,
        builder.divisibleBy!!,
        builder.ifTrueThrowTo!!,
        builder.ifFalseThrowTo!!
    )
}

fun makeMonkeys(input: List<String>): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    var builder = Monkey.Builder()

    input.map { it.trim() }.forEach { line ->
        with(line) {
            when {
                startsWith("Monkey") -> {
                    val number = split(" ")[1].let { it.substring(0, it.length - 1) }
                    builder = Monkey.Builder().number(number.toInt())
                }

                startsWith("Starting") -> {
                    builder.items(split(": ")[1].split(", ").map { it.toLong() }.toMutableList())
                }

                startsWith("Operation") -> {
                    builder.operation(split("= ")[1])
                }

                startsWith("Test") -> {
                    builder.divisibleBy(split("divisible by ")[1].toInt())
                }

                startsWith("If true") -> {
                    builder.ifTrueThrowTo(split("monkey ")[1].toInt())
                }

                startsWith("If false") -> {
                    builder.ifFalseThrowTo(split("monkey ")[1].toInt())
                    monkeys.add(builder.build())
                }
            }
        }
    }

    return monkeys
}