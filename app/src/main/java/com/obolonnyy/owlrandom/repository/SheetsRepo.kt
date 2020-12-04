package com.obolonnyy.owlrandom.repository

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest
import com.google.api.services.sheets.v4.model.ValueRange
import com.obolonnyy.owlrandom.model.PicturedWord
import com.obolonnyy.owlrandom.utils.log


const val spreadsheetId = "1b_itSmdduwk6OX_fXr7GrReSAEeuqU27jCJMFEmcWi8"

class SheetsRepo(
    private val service: Sheets
) {
    suspend fun postResuls(
        words: List<PicturedWord>,
        answered: MutableSet<Int>,
        notAnswered: MutableSet<Int>
    ) {

        val valueRanges = mutableListOf<ValueRange>()
        words.forEachIndexed { index, picturedWord ->
            var w = picturedWord.word
            if (answered.contains(index)) {
                w = picturedWord.word.copy(answered = w.answered + 1)
            } else if (notAnswered.contains(index)) {
                w = picturedWord.word.copy(notAnswered = w.notAnswered + 1)
            }
            val values = listOf(w.answered, w.notAnswered).map { it.toString() }
            val range = "E${w.rowNumber}:F${w.rowNumber}"
            valueRanges.add(ValueRange().setRange(range).setValues(listOf(values)))
        }
        val batches = BatchUpdateValuesRequest()
            .setValueInputOption("USER_ENTERED")
            .setData(valueRanges)
        service.spreadsheets().values()
            .batchUpdate(spreadsheetId, batches)
            .execute()

        log("SheetsRepo, ended request")
    }
}