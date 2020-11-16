package com.obolonnyy.owlrandom.core

import android.net.Uri
import okhttp3.Response

interface FileStorage {
    suspend fun saveFile(response: Response, uri: Uri, fileName: String): Uri
    suspend fun getFile(documentName: String): Uri?
}