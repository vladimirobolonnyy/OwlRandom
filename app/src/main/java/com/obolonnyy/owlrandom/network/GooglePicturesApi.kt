package com.obolonnyy.owlrandom.network

import com.obolonnyy.owlrandom.app.BuildInfo.ApiKey
import com.obolonnyy.owlrandom.app.BuildInfo.CX
import retrofit2.http.GET
import retrofit2.http.Query


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