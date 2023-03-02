package com.andymur.langs.de.server
import com.andymur.langs.de.model.HelperVerb
import com.andymur.langs.de.model.Verb
import io.javalin.Javalin

// see https://javalin.io/tutorials/simple-kotlin-example
fun main() {
    val app = Javalin.create().start(7070)
    app.get("/") { ctx -> ctx.json(Verb(infinitive = "sein", present = "ist", past = "war", perfect = "gewesen", helper = HelperVerb.SEIN)) }
}