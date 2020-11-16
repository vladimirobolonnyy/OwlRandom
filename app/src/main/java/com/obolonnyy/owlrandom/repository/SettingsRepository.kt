package com.obolonnyy.owlrandom.repository

interface SettingsRepository {
    suspend fun getUserDesiredCount(): Int
}

class SettingsRepositoryImpl : SettingsRepository {

    //todo implement later
    override suspend fun getUserDesiredCount(): Int = 5

}