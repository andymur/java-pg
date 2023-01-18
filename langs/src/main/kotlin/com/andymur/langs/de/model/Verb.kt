package com.andymur.langs.de.model

data class Verb(
    val infinitive: String,
    val present: String,
    val past: String,
    val perfect: String,
    val helper: HelperVerb
)

enum class HelperVerb {
    HABEN, SEIN
}
