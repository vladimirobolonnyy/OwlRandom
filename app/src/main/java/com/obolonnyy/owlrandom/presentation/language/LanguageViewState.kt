package com.obolonnyy.owlrandom.presentation.language

import com.obolonnyy.owlrandom.model.PicturedWord

data class LanguageViewState(
    val words: List<PicturedWord>,
    private val answered: Int = 0,
    private val notAnswered: Int = 0,
    val currentItem: Int = 0,
    val showBottom: Boolean = false,
    val showEngTop: Boolean = true,
    val clickEnable: Boolean = true
) {

    init {
        if (words.isEmpty()) error("empty list")
    }

    private val maxItems: Int = words.size
    private val canGoNext = currentItem + 1 >= maxItems
    val isFull = answered + notAnswered >= maxItems

    fun next(answered: Int, notAnswered: Int): LanguageViewState {
        return this.copy(
            currentItem = if (canGoNext) currentItem else currentItem + 1,
            showBottom = false,
            answered = answered,
            notAnswered = notAnswered
        )
    }

    fun prev(answered: Int, notAnswered: Int): LanguageViewState {
        if (currentItem == 0) return this
        return this.copy(
            currentItem = currentItem - 1,
            showBottom = false,
            answered = answered,
            notAnswered = notAnswered
        )
    }

    val goodBadRating: String = "$answered / $notAnswered"

    private val word = words[currentItem].word
    val pictures = words[currentItem].googlePicture
    private val english: String = word.english.joinToString(" / ")
    private val russian: String = word.russian.joinToString(" / ")
    val showedAnswered: String = "answered: ${word.answered} / notAnswered: ${word.notAnswered}"

    val countWords = "${currentItem + 1} / $maxItems"
    val topWord = if (showEngTop) english else russian
    val bottomWord = if (showEngTop) russian else english
}
