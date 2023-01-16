package com.andymur.langs.de

import com.andymur.langs.de.helper.CsvVerbReader
import com.andymur.langs.de.model.Verb

fun main() {
    val verbReader = CsvVerbReader()
    val verbs = verbReader.readFile(filename = "verbs.csv", hasHeader = false)
    val mappedVerbs = mapVerbs(verbs)
    // random list (+ number of questions)
    // w/ or w/o repetitions
    // where were errors?
    // w/ or w/o helper verb: haben or sein
    var errors = 0
    verbs.shuffled().forEach {
        println(it.infinitive)
        val (past, perfect) = readln().split(" ")
        val currentVerb = mappedVerbs[it.infinitive]
        errors += if (currentVerb?.past != past && currentVerb?.past?.replace("ß", "ss") != past) 1 else 0
        errors += if(currentVerb?.perfect != perfect) 1 else 0
    }
    println("ERRORS: $errors")
}

fun mapVerbs(verbsList: List<Verb>): Map<String, Verb> = verbsList.associateBy { it.infinitive }