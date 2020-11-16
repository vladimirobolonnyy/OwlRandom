package com.obolonnyy.owlrandom.domain

import com.obolonnyy.owlrandom.model.GooglePicture
import com.obolonnyy.owlrandom.model.PicturedWord
import com.obolonnyy.owlrandom.presentation.details.Randomizer
import com.obolonnyy.owlrandom.repository.*
import com.obolonnyy.owlrandom.utils.asyncAll

class WordsInteractor(
    private val wordsRepo: GoogleSheetsRepository = GoogleSheetsRepositoryImpl(),
    private val picturesRepo: GooglePicturesRepository = GooglePicturesRepositoryImpl(),
    private val settingsRepo: SettingsRepository = SettingsRepositoryImpl(),
    private val random: Randomizer = Randomizer(),
) {

    suspend fun invoke(): List<PicturedWord> {
        val words = wordsRepo.getAllWords()
        val shuffledWords = random.shuffle(words.toMutableList())
        val userDesiredCount = settingsRepo.getUserDesiredCount()
        val resultWords = if (shuffledWords.size < userDesiredCount) {
            shuffledWords
        } else {
            shuffledWords.subList(0, userDesiredCount)
        }
        val pictures = kotlin.runCatching {
            asyncAll(resultWords) { picturesRepo.getPictures(it.englishOne) }
        }.getOrNull()
        val resultList = mutableListOf<PicturedWord>()
        for (i in 0 until resultWords.size) {
            val word = resultWords[i]
            resultList.add(PicturedWord(word, pictures?.get(i) ?: GooglePicture(word.englishOne)))
        }
        return resultList
    }
}