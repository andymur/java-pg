package com.andymur.langs.de.helper

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
                val (infinitive, past, perfect) = it.split(delimiter, limit = 3)
                Verb(infinitive = infinitive, present = infinitive, past = past, perfect = perfect)
            }.toList()
        }
        return emptyList()
    }
}