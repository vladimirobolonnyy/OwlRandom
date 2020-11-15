package com.obolonnyy.owlrandom.database

interface SettingsRepository {
    suspend fun getUserDesiredCount(): Int
}

class SettingsRepositoryImpl : SettingsRepository {

    //todo implement later
    override suspend fun getUserDesiredCount(): Int = 10

}