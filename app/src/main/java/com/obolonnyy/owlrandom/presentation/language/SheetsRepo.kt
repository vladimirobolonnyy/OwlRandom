package com.obolonnyy.owlrandom.presentation.language

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.obolonnyy.owlrandom.model.PicturedWord

const val spreadsheetId = "1b_itSmdduwk6OX_fXr7GrReSAEeuqU27jCJMFEmcWi8"

class SheetsRepo(
    private val service: Sheets
) {
    suspend fun postResuls(
        words: List<PicturedWord>,
        answered: MutableSet<Int>,
        notAnswered: MutableSet<Int>
    ) {

        words.forEachIndexed { index, picturedWord ->
            var w = picturedWord.word
            if (answered.contains(index)) {
                w = picturedWord.word.copy(answered = w.answered + 1, showed = w.showed + 1)
            } else if (notAnswered.contains(index)) {
                w = picturedWord.word.copy(notAnswered = w.notAnswered + 1, showed = w.showed + 1)
            }
            val values: MutableList<List<String>> = mutableListOf()
            values.add(listOf(w.showed, w.answered, w.notAnswered).map { it.toString() })
            val body: ValueRange = ValueRange().setValues(values.toList())
            val range = "D${w.rowNumber}:F${w.rowNumber}"
            service.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption("USER_ENTERED")
                .execute()
        }
    }

}