package com.obolonnyy.owlrandom.network

import com.obolonnyy.owlrandom.app.BuildInfo.ApiKey
import com.obolonnyy.owlrandom.app.BuildInfo.SheetId
import retrofit2.http.GET
import retrofit2.http.Query


const val RangeMin = 2
private const val Range = "english!A${RangeMin}:F1000"

interface SheetsApi {

    @GET("${SheetId}/values/${Range}")
    suspend fun getAllWords(
        @Query("key") key: String = ApiKey
    ): GoogleSheetsAnswerDto
}