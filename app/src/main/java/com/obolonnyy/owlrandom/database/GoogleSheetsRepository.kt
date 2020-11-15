package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.model.Word
import com.obolonnyy.owlrandom.network.NetworkModule
import com.obolonnyy.owlrandom.network.SheetsApi


interface GoogleSheetsRepository {
    suspend fun getAllWords(): List<Word>
}

class GoogleSheetsRepositoryImpl(
    val api: SheetsApi = NetworkModule.sheetsApi
) : GoogleSheetsRepository {

    override suspend fun getAllWords(): List<Word> {
        return api.getAllWords().convert()
    }
}