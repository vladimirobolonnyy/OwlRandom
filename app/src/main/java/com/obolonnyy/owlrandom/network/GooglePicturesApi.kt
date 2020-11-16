package com.obolonnyy.owlrandom.network

import retrofit2.http.GET
import retrofit2.http.Query

private const val ApiKey = "AIzaSyDkYHGDk1IyyJu1RnmaMLqDuP6SVU84htg"
private const val CX = "d7a80698f10795d96"

interface GooglePicturesApi {

    @GET("customsearch/v1")
    suspend fun getPicture(
        @Query("q") text: String,
        @Query("key") key: String = ApiKey,
        @Query("cx") cx: String = CX,
        @Query("alt") alt: String = "json",
        @Query("searchType") searchType: String = "image",
        @Query("lr") lr: String = "lang_en",
        @Query("imgSize") imgSize: String = "large",
    ): GooglePictureDto

}