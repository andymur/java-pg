package com.andymur.langs.de.helper

import com.andymur.langs.de.model.HelperVerb
import com.andymur.langs.de.model.Verb
import java.io.File

class CsvVerbReader {
    fun readFile(filename: String, hasHeader: Boolean = true, delimiter: String = ";"): List<Verb> {
        val uri = javaClass.classLoader.getResource(filename)?.toURI()
        uri?.let {
            val reader = File(it).bufferedReader()
            if (hasHeader) {
                reader.readLine()
            }
            return reader.lineSequence().map {
                val (infinitive, present, past, perfect, helper) = it.split(delimiter, limit = 5)
                Verb(
                    infinitive = infinitive, present = present, past = past, perfect = perfect,
                    helper = when (helper) {
                        "haben" -> HelperVerb.HABEN
                        "sein" -> HelperVerb.SEIN
                        else -> HelperVerb.HABEN
                    }
                )
            }.toList()
        }
        return emptyList()
    }
}