package com.obolonnyy.owlrandom.network

import retrofit2.http.GET

private const val SheetId = "1b_itSmdduwk6OX_fXr7GrReSAEeuqU27jCJMFEmcWi8"
private const val ApiKey = "AIzaSyDkYHGDk1IyyJu1RnmaMLqDuP6SVU84htg"
private const val Range = "english!A2:F1000"

interface SheetsApi {

    @GET("${SheetId}/values/${Range}?key=$ApiKey")
    suspend fun getAllWords(): GoogleSheetsAnswerDto
}