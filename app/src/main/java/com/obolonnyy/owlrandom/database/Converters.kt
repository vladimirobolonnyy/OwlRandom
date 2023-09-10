package com.obolonnyy.owlrandom.database

import androidx.room.TypeConverter
import com.obolonnyy.owlrandom.app.Modules
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json: Json = Modules.json

    @TypeConverter
    fun listMyModelToJsonStr(listMyModel: List<String>?): String? {
        listMyModel ?: return null
        return json.encodeToString(listMyModel)
    }

    @TypeConverter
    fun jsonStrToListMyModel(jsonStr: String?): List<String>? {
        jsonStr ?: return null
        return json.decodeFromString<List<String>>(jsonStr)
    }
}