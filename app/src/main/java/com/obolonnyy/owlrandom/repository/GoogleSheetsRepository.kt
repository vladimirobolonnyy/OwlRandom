package com.obolonnyy.owlrandom.repository

import com.obolonnyy.owlrandom.core.SimpleStore
import com.obolonnyy.owlrandom.database.Caches
import com.obolonnyy.owlrandom.model.Word
import com.obolonnyy.owlrandom.network.NetworkModule
import com.obolonnyy.owlrandom.network.SheetsApi


interface GoogleSheetsRepository {
    suspend fun getAllWords(): List<Word>
    suspend fun getAllWordsMap(): Map<String, Word>
}

class GoogleSheetsRepositoryImpl(
    private val api: SheetsApi = NetworkModule.sheetsApi,
    private val store: SimpleStore = Caches.wordsCache
) : GoogleSheetsRepository {

    private val key = "language_words"

    override suspend fun getAllWords(): List<Word> {
        return api.getAllWords().convert()
            .also { store.set(key, it) }
    }

    override suspend fun getAllWordsMap(): Map<String, Word> {
        return api.getAllWords().convert().associateBy({ it.englishOne }, { it })
    }
}