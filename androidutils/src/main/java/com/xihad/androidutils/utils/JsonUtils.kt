package com.xihad.androidutils.utils


import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * Created by @Ziad Islam on 01/11/2023.
 * Optimized utility for JSON serialization/deserialization with robust error handling
 */
object JsonUtils {

    // Reusable Gson instance (thread-safe and more efficient than creating new instances)
    val gson: Gson by lazy {
        GsonBuilder()
            .serializeNulls() // Handle null values explicitly
            .setLenient() // More forgiving parsing
            .create()
    }

    /**
     * Deep copy an object via JSON serialization
     * @return Copy of the object or null if operation fails
     */
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T> copyObject(data: Any): T {
        return try {
            val jsonElement = gson.toJsonTree(data)
            gson.fromJson(jsonElement, data.javaClass as Type) as? T
                ?: data as T  // Fallback if fromJson returns null
        } catch (e: Exception) {
            logError("copyObject", e, "Returning original object")
            data as T  // Fallback on any exception
        }
    }


    /**
     * Convert any object to JSON string
     * @return JSON string or empty string if serialization fails
     */
    @JvmStatic
    fun <T> T.toJson(): String {
        return try {
            gson.toJson(this)
        } catch (e: Exception) {
            logError("toJson", e)
            ""
        }
    }

    /**
     * Convert any object to JSON string (safe version with null handling)
     * @return JSON string or null if serialization fails
     */
    @JvmStatic
    fun <T> T?.toJsonOrNull(): String? {
        return try {
            this?.let { gson.toJson(it) }
        } catch (e: Exception) {
            logError("toJsonOrNull", e)
            null
        }
    }

    /**
     * Convert org.json.JSONObject to Gson JsonObject
     */
    @JvmStatic
    fun JSONObject.toJsonObject(): JsonObject {
        return try {
            gson.fromJson(this.toString(), JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            logError("toJsonObject", e)
            JsonObject()
        }
    }

    @JvmStatic
    fun <T> T?.toJsonObject(): JsonObject {
        return try {
            gson.fromJson(this.toJson(), JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            logError("toJsonObject", e)
            JsonObject()
        }
    }


    /**
     * Parse JSON string to model object
     * @return Parsed object or null if parsing fails
     */
    @JvmStatic
    fun <T> String?.toModel(clazz: Class<T>): T? {
        if (this.isNullOrBlank()) return null

        return try {
            gson.fromJson(this, clazz)
        } catch (e: JsonSyntaxException) {
            logError("toModel(String)", e, "JSON: ${this.take(100)}")
            null
        } catch (e: Exception) {
            logError("toModel(String)", e)
            null
        }
    }

    /**
     * Parse JSON string to model object
     * @return Parsed object or null if parsing fails
     */
    @JvmStatic
    fun <T> String?.fromJson(clazz: Class<T>): T? {
        if (this.isNullOrBlank()) return null

        return try {
            gson.fromJson(this, clazz)
        } catch (e: JsonSyntaxException) {
            logError("toModel(String)", e, "JSON: ${this.take(100)}")
            null
        } catch (e: Exception) {
            logError("toModel(String)", e)
            null
        }
    }

    /**
     * Parse JSONObject to model object
     * @return Parsed object or null if parsing fails
     */
    @JvmStatic
    fun <T> JSONObject?.toModel(clazz: Class<T>): T? {
        if (this == null) return null

        return try {
            gson.fromJson(this.toString(), clazz)
        } catch (e: JsonSyntaxException) {
            logError("toModel(JSONObject)", e)
            null
        } catch (e: JSONException) {
            logError("toModel(JSONObject)", e, "Invalid JSONObject")
            null
        } catch (e: Exception) {
            logError("toModel(JSONObject)", e)
            null
        }
    }

    /**
     * Parse JSON string to list of model objects
     * @return Parsed list or empty list if parsing fails
     */
    @JvmStatic
    fun <T> String?.toModelList(clazz: Class<T>): List<T> {
        if (this.isNullOrBlank()) return emptyList()

        return try {
            val typeOfT: Type = TypeToken.getParameterized(List::class.java, clazz).type
            gson.fromJson(this, typeOfT) ?: emptyList()
        } catch (e: JsonSyntaxException) {
            logError("toModelList(String)", e, "JSON: ${this.take(100)}")
            emptyList()
        } catch (e: Exception) {
            logError("toModelList(String)", e)
            emptyList()
        }
    }

    /**
     * Parse JSONArray to list of model objects
     * @return Parsed list or empty list if parsing fails
     */
    @JvmStatic
    fun <T> JSONArray?.toModelList(clazz: Class<T>): List<T> {
        if (this == null) return emptyList()

        return try {
            val typeOfT: Type = TypeToken.getParameterized(List::class.java, clazz).type
            gson.fromJson(this.toString(), typeOfT) ?: emptyList()
        } catch (e: JsonSyntaxException) {
            logError("toModelList(JSONArray)", e)
            emptyList()
        } catch (e: JSONException) {
            logError("toModelList(JSONArray)", e, "Invalid JSONArray")
            emptyList()
        } catch (e: Exception) {
            logError("toModelList(JSONArray)", e)
            emptyList()
        }
    }

    /**
     * Get Type for List<T> (reified version for better type safety)
     */
    inline fun <reified T> typeOfList(): Type {
        return object : TypeToken<List<T>>() {}.type
    }

    /**
     * Parse JSON array string to list of model objects
     * Alias for toModelList for backwards compatibility
     * @return Parsed list or empty list if parsing fails
     */
    fun <T> getList(jsonArray: String?, clazz: Class<T>): List<T> {
        return jsonArray.toModelList(clazz)
    }

    /**
     * Check if a string is valid JSON
     */
    @SuppressLint("CheckResult")
    fun String?.isValidJson(): Boolean {
        if (this.isNullOrBlank()) return false

        return try {
            gson.fromJson(this, JsonObject::class.java)
            true
        } catch (e: JsonSyntaxException) {
            false
        }
    }

    /**
     * Safe JSON parsing with default value
     */
    @JvmStatic
    fun <T> String?.toModelOrDefault(clazz: Class<T>, default: T): T {
        return this.toModel(clazz) ?: default
    }

    /**
     * Parse JSON with custom type
     */
    @JvmStatic
    fun <T> String?.toModelWithType(type: Type): T? {
        if (this.isNullOrBlank()) return null

        return try {
            gson.fromJson(this, type)
        } catch (e: JsonSyntaxException) {
            logError("toModelWithType", e)
            null
        } catch (e: Exception) {
            logError("toModelWithType", e)
            null
        }
    }

    /**
     * Convert Map to JSON string
     */
    @JvmStatic
    fun Map<*, *>.toJson(): String {
        return try {
            gson.toJson(this)
        } catch (e: Exception) {
            logError("Map.toJson", e)
            "{}"
        }
    }

    /**
     * Parse JSON string to Map
     */
    @JvmStatic
    fun String?.toMap(): Map<String, Any> {
        if (this.isNullOrBlank()) return emptyMap()

        return try {
            val type = object : TypeToken<Map<String, Any>>() {}.type
            gson.fromJson(this, type)
        } catch (e: Exception) {
            logError("toMap", e)
            emptyMap()
        }
    }

    @JvmStatic
    fun String?.toStringMap(): Map<String, String> {
        if (this.isNullOrBlank()) return emptyMap()

        return try {
            val type = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson<Map<String, String>>(this, type) ?: emptyMap()
        } catch (e: Exception) {
            logError("toStringMap", e)
            emptyMap()
        }
    }

    @JvmStatic
    fun String?.toIntMap(): Map<String, Int> {
        if (this.isNullOrBlank()) return emptyMap()

        return try {
            val type = object : TypeToken<Map<String, Int>>() {}.type
            gson.fromJson<Map<String, Int>>(this, type) ?: emptyMap()
        } catch (e: Exception) {
            logError("toStringMap", e)
            emptyMap()
        }
    }


    /**
     * Pretty print JSON string
     */
    @JvmStatic
    fun String?.toPrettyJson(): String? {
        if (this.isNullOrBlank()) return null

        return try {
            val jsonElement = gson.fromJson(this, JsonObject::class.java)
            GsonBuilder().setPrettyPrinting().create().toJson(jsonElement)
        } catch (e: Exception) {
            logError("toPrettyJson", e)
            this
        }
    }


    /**
     * Centralized error logging with lazy evaluation
     */
    private inline fun logErrorMsg(
        method: String,
        error: Throwable,
        crossinline additionalInfo: () -> String = { "" }
    ) {
        Log.eLazy {
            buildString {
                append("JsonUtils.$method failed: ${error.message}")
                val info = additionalInfo()
                if (info.isNotBlank()) {
                    append(" | $info")
                }
            }
        }
        // Also log the exception with stack trace
        Log.e("JsonUtils", "JsonUtils.$method failed", error)
    }

    /**
     * Backward compatibility - String overload
     */
    private fun logError(method: String, error: Throwable, additionalInfo: String = "") {
        logErrorMsg(method, error) { additionalInfo }
    }

}