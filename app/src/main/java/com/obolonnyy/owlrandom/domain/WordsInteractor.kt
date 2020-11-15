package com.obolonnyy.owlrandom.domain

import com.obolonnyy.owlrandom.database.GoogleSheetsRepository
import com.obolonnyy.owlrandom.database.GoogleSheetsRepositoryImpl
import com.obolonnyy.owlrandom.database.SettingsRepository
import com.obolonnyy.owlrandom.database.SettingsRepositoryImpl
import com.obolonnyy.owlrandom.model.Word
import com.obolonnyy.owlrandom.presentation.details.Randomizer

class WordsInteractor(
    private val wordsRepo: GoogleSheetsRepository = GoogleSheetsRepositoryImpl(),
    private val settingsRepo: SettingsRepository = SettingsRepositoryImpl(),
    private val random: Randomizer = Randomizer(),
) {

    suspend fun invoke(): List<Word> {
        val words = wordsRepo.getAllWords()
        val shuffledWords = random.shuffle(words.toMutableList())
        val userDesiredCount = settingsRepo.getUserDesiredCount()
        return if (shuffledWords.size < userDesiredCount) {
            shuffledWords
        } else {
            shuffledWords.subList(0, userDesiredCount)
        }
    }
}