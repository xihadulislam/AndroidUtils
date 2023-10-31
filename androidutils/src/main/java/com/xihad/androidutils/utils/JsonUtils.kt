package com.xihad.androidutils.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject

object JsonUtils {

    fun <T> T.toJson(): String {
        return Gson().toJson(this)
    }

    fun JSONObject.toJsonObject(): JsonObject = Gson().fromJson(this.toString(), JsonObject::class.java)
}