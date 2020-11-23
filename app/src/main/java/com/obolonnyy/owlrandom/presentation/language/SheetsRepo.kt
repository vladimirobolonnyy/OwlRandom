package com.obolonnyy.owlrandom.presentation.language

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.obolonnyy.owlrandom.model.PicturedWord
import com.obolonnyy.owlrandom.model.Word

const val spreadsheetId = "1b_itSmdduwk6OX_fXr7GrReSAEeuqU27jCJMFEmcWi8"

class SheetsRepo(
    private val service: Sheets
) {
    suspend fun postResuls(
        words: List<PicturedWord>,
        answered: MutableSet<Int>,
        notAnswered: MutableSet<Int>
    ) {

        //todo remove in interactor
        val max: Int = (words.maxByOrNull { it.word.rowNumber }?.word?.rowNumber?.inc() ?: 400)
        val min = 2
        val range = "D${min}:F${max}"
        val resultList = MutableList<Word?>(max) { index -> null }

        words.forEachIndexed { index, picturedWord ->
            var w = picturedWord.word
            w = w.copy(showed = w.showed + 1)
            if (answered.contains(index)) {
                w = w.copy(answered = w.answered + 1)
            } else if (notAnswered.contains(index)) {
                w = w.copy(notAnswered = w.notAnswered + 1)
            }
            resultList.add(w.rowNumber, w)
        }

        val values: MutableList<List<String>> = mutableListOf()
        for (index in min until max) {
            val word = resultList[index]
            if (word != null) {
                values.add(
                    listOf(
                        word.showed,
                        word.answered,
                        word.notAnswered
                    ).map { it.toString() })
            } else {
                values.add(listOf("", "", ""))
            }
        }
        val body: ValueRange = ValueRange().setValues(values.toList())
        service.spreadsheets().values().update(spreadsheetId, range, body)
            .setValueInputOption("USER_ENTERED")
            .execute()
    }

}