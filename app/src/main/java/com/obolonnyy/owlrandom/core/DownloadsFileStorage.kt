package com.obolonnyy.owlrandom.core


import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.obolonnyy.owlrandom.app.BuildInfo
import com.obolonnyy.owlrandom.app.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.Source
import okio.buffer
import okio.sink
import java.io.File


/**
 * Вспомогательный класс для сохранения файлов во внутреннюю память и получения файлов.
 * Имеет дефолтную стратегию по хранению файлов. При инициализации удаляются все файлы
 * из папки [pathFolder], которые были созданы более [daysToKeep] назад.
 *
 * @param pathFolder - название папки, где будут храниться файлы.
 */
class DownloadsFileStorage(
    private val context: Context = MainApplication.context,
    private val pathFolder: String,
) : FileStorage {

    init {
        createFolderIfNotExists()
    }

    override suspend fun saveFile(response: okhttp3.Response, uri: Uri, fileName: String): Uri {
        return withContext(Dispatchers.IO) {
            saveFile(response.body!!.source(), fileName)
        }
    }

    override suspend fun getFile(documentName: String): Uri? {
        val file = getLocalFile(documentName)
        if (file.exists().not()) return null
        return getLocalUri(file)
    }

    private fun saveFile(source: Source, fileName: String): Uri {
        val file = getLocalFile(fileName)
        val sink = file.sink().buffer()
        sink.writeAll(source)
        sink.close()
        return getLocalUri(file)
    }

    private fun createFolderIfNotExists() {
        val folder = getFilesDir()
        if (!folder.exists()) {
            folder.mkdir()
        }
    }

    private fun getFilesDir() = File(context.filesDir.path + "/${pathFolder}/")

    private fun getLocalFile(fileName: String) =
        File(context.filesDir.path + "/${pathFolder}/" + fileName)

    private fun getLocalUri(file: File): Uri {
        return file.toContentUri(context)
    }
}

private fun File.toContentUri(context: Context): Uri {
    return FileProvider.getUriForFile(context, "${BuildInfo.appPackage}.fileprovider", this)
}