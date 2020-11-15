package com.obolonnyy.owlrandom.presentation.language

import com.obolonnyy.owlrandom.model.Word

data class LanguageViewState(
    val goodBadRating: String = "", // todo think
    private val english: String,
    private val russian: String,
    val showedAnswered: String = "",
    val showBottom: Boolean = false,
    val currentItem: Int = 0,
    val maxItems: Int = 0,
    val showEngTop: Boolean = true
) {
    val countWords = "${currentItem + 1} / $maxItems"
    val topWord = if (showEngTop) english else russian
    val bottomWord = if (showEngTop) russian else english

    constructor(word: Word, currentItem: Int, maxItems: Int) : this(
        english = word.english.joinToString(" / "),
        russian = word.russian.joinToString(" / "),
        currentItem = currentItem,
        maxItems = maxItems
    )
}