package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.core.SimpleStore
import com.obolonnyy.owlrandom.model.Word
import com.obolonnyy.owlrandom.network.NetworkModule
import com.obolonnyy.owlrandom.network.SheetsApi


interface GoogleSheetsRepository {
    suspend fun getAllWords(): List<Word>
}

class GoogleSheetsRepositoryImpl(
    private val api: SheetsApi = NetworkModule.sheetsApi,
    private val store: SimpleStore = Caches.wordsCache
) : GoogleSheetsRepository {

    private val key = "language_words"

    override suspend fun getAllWords(): List<Word> {
        val cached = store.get(key) as? List<Word>
        return cached ?: api.getAllWords().convert()
            .also { store.set(key, it) }
    }
}