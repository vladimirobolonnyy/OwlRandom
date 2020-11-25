package com.obolonnyy.owlrandom.network

import com.obolonnyy.owlrandom.model.Word

class GoogleSheetsAnswerDto {

    private val values: List<List<String>>? = null

    fun convert(): List<Word> {
        val result = mutableListOf<Word>()
        val startIndex = RangeMin // because in googlesheets we start from index 2
        values?.forEachIndexed { index, triple ->
            val first = triple.getOrNull(0)
            val second = triple.getOrNull(1)
            val third = triple.getOrNull(2)
            val showed = triple.getOrNull(3)?.toIntOrNull() ?: 0
            val answered = triple.getOrNull(4)?.toIntOrNull() ?: 0
            val notAnswered = triple.getOrNull(5)?.toIntOrNull() ?: 0
            val word =
                getWord(startIndex + index, first, second, third, showed, answered, notAnswered)
            if (word != null) {
                result.add(word)
            }
        }
        return result
    }

    private fun getWord(
        index: Int,
        s1: String?,
        s2: String?,
        s3: String?,
        showed: Int,
        answered: Int,
        notAnswered: Int
    ): Word? {
        if (s1.isNullOrEmpty()) return null
        if (s2.isNullOrEmpty()) return null
        val english = s1.split("/").filter { it.isNotEmpty() }
        var russian = s2.split("/") + (s3?.split("/") ?: emptyList())
        russian = russian.filter { it.isNotEmpty() }
        return Word(index, english, russian, showed, answered, notAnswered)
    }
}