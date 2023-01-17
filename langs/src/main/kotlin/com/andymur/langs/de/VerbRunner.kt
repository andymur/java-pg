package com.andymur.langs.de

import com.andymur.langs.de.helper.CsvVerbReader
import com.andymur.langs.de.model.Verb
import com.andymur.langs.de.model.VerbError

fun main() {
    runIrregularVerbsTest()
}

fun runIrregularVerbsTest() {
    val verbReader = CsvVerbReader()
    val verbs = verbReader.readFile(filename = "verbs.csv", hasHeader = false)
    // w/ or w/o helper verb: haben or sein
    val verbErrors: ArrayList<VerbError> = ArrayList()

    prepareVerbList(verbs, 3, false).forEach {
        println(it.infinitive)
        val (past, perfect) = readln().split(" ")
        verbErrors.add(
            compareVerbs(infinitive = it.infinitive, expectedVerb = it.past, past)
        )
        verbErrors.add(
            compareVerbs(infinitive = it.infinitive, expectedVerb = it.perfect, perfect)
        )
    }
    val errors = verbErrors.filter { it.hasError() }.count()
    println("ERRORS: $errors")
    verbErrors.filter { it.hasError() }.forEach { println(it.format()) }
}

fun VerbError.format() = "An error for ${this.infinitive} ${this.actual} should be ${this.expected}"

fun VerbError.hasError() = this.infinitive.isNotEmpty()

fun compareVerbs(infinitive: String, expectedVerb: String, actualVerb: String): VerbError =
    if (expectedVerb != actualVerb && expectedVerb.replace("ß", "ss") != actualVerb)
        VerbError(infinitive = infinitive, expected = expectedVerb, actual = actualVerb)
    else
        VerbError()

fun prepareVerbList(verbs: List<Verb>, questionsNumber: Int, unique: Boolean = false): Collection<Verb> {
    val numberOfCopies = ((questionsNumber - 1) / verbs.size) + 1
    val resultedList = nCopies(numberOfCopies, verbs).shuffled().subList(0, questionsNumber)
    return if (unique) HashSet(resultedList) else resultedList
}

fun <T> nCopies(n: Int, verbs: List<T>): List<T> {
    val result: MutableList<T> = ArrayList(n * verbs.size)
    for (i in 1..n) {
        result.addAll(elements = verbs)
    }
    return result
}