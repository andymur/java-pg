package com.andymur.langs.de.model

data class VerbError(
    val infinitive: String = "",
    val present: String = "",
    val expected: String = "",
    val actual: String = "",
    val helper: String = "",
    val verbTense: VerbTense = VerbTense.PRESENT
)

enum class VerbTense {
    PRESENT, PAST, PERFECT
}