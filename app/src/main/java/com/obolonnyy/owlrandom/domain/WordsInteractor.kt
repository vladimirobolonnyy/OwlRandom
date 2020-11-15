package com.obolonnyy.owlrandom.domain

import com.obolonnyy.owlrandom.database.GoogleSheetsRepository
import com.obolonnyy.owlrandom.database.GoogleSheetsRepositoryImpl
import com.obolonnyy.owlrandom.database.SettingsRepository
import com.obolonnyy.owlrandom.database.SettingsRepositoryImpl
import com.obolonnyy.owlrandom.model.Word
import kotlin.random.Random

class WordsInteractor(
    private val wordsRepo: GoogleSheetsRepository = GoogleSheetsRepositoryImpl(),
    private val settingsRepo: SettingsRepository = SettingsRepositoryImpl(),
    private val random: Random = Random(1000)
) {

    suspend fun invoke(): List<Word> {
        val words = wordsRepo.getAllWords()
        val shuffledWords = words.shuffled(random)
        val userDesiredCount = settingsRepo.getUserDesiredCount()
        return if (shuffledWords.size < userDesiredCount) {
            shuffledWords
        } else {
            shuffledWords.subList(0, userDesiredCount)
        }
    }
}