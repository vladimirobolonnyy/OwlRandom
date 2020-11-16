package com.obolonnyy.owlrandom.core

import android.net.Uri
import com.obolonnyy.owlrandom.utils.await
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

interface ProxyCacheServer {
    suspend fun getProxyUrl(uri: String, fileName: String): Uri
    suspend fun getProxyUrl(uri: Uri, fileName: String): Uri
}

/**
 * Вспомогательный класс для скачивания файлов для ситуаций, когда Glide'a не хватает.
 * [UriProxyCacheServer] возвращает [Uri], куда будет скачен файл.
 */
class UriProxyCacheServer(
    private val client: OkHttpClient = OkHttpClient(),
    private val fileStorage: FileStorage = DownloadsFileStorage(pathFolder = "pictures"),
) : ProxyCacheServer {

    override suspend fun getProxyUrl(uri: String, fileName: String): Uri {
        return getProxyUrl(Uri.parse(uri), fileName)
    }

    override suspend fun getProxyUrl(uri: Uri, fileName: String): Uri {
        return fileStorage.getFile(fileName) ?: downloadAndSave(uri, fileName)
    }

    private suspend fun downloadAndSave(uri: Uri, fileName: String): Uri {
        return getRemoteFile(uri.toString()).let { response ->
            fileStorage.saveFile(response, uri, fileName)
        }
    }

    private suspend fun getRemoteFile(url: String): Response {
        val request: Request = Request.Builder().url(url).build()
        return client.newCall(request).await()
    }
}