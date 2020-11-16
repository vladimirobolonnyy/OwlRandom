package com.obolonnyy.owlrandom.repository

import com.obolonnyy.owlrandom.core.Clock
import com.obolonnyy.owlrandom.core.RealtimeClock
import com.obolonnyy.owlrandom.database.LANGUAGE_DATABASE
import com.obolonnyy.owlrandom.database.LanguageDao
import com.obolonnyy.owlrandom.database.toEntity
import com.obolonnyy.owlrandom.database.toModel
import com.obolonnyy.owlrandom.model.GooglePicture
import com.obolonnyy.owlrandom.network.GooglePicturesApi
import com.obolonnyy.owlrandom.network.NetworkModule


interface GooglePicturesRepository {
    suspend fun getPictures(text: String): GooglePicture
}

class GooglePicturesRepositoryImpl(
    private val api: GooglePicturesApi = NetworkModule.googlePicturesApi,
    private val dao: LanguageDao = LANGUAGE_DATABASE.languageDao(),
    private val clock: Clock = RealtimeClock()
) : GooglePicturesRepository {

    override suspend fun getPictures(text: String): GooglePicture {
        val cached = dao.getPictureBy(text)?.toModel()
        return cached ?: api.getPicture(text).convert(text)
            .also { dao.savePicture(it.toEntity(date = clock.nowSeconds())) }
    }
}