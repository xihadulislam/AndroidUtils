package com.xihad.androidutils.utils.sort

import org.json.JSONArray
import org.json.JSONException
import java.util.Collections

object SortUtils {

    fun getComaString(selectedTables: List<String>): String {
        return appendStringWithComa(sortComaString(selectedTables))
    }

    fun getComaString(selectedTables: JSONArray?): String {
        return appendStringWithComa(sortComaString(selectedTables))
    }

    fun appendStringWithComa(selectedTables: List<String>): String {
        var tables = StringBuilder()
        try {
            for (i in selectedTables.indices) {
                tables.append(",").append(selectedTables[i])
            }
            if (tables.toString().isNotEmpty()) tables = StringBuilder(tables.substring(1))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tables.toString()
    }


    fun sortComaString(data: List<String>): List<String> {
        val convertedData = ArrayList<String>()
        for (s in data) {
            convertedData.add(s.replace("[", "").replace("]", ""))
        }
        return alphaNumSort(convertedData)
    }

    fun sortComaString(data: JSONArray?): List<String> {
        val convertedData = ArrayList<String>()
        data?.let {
            if (it.length() > 0) {
                for (i in 0 until it.length()) {
                    try {
                        convertedData.add(it.getString(i).replace("[", "").replace("]", ""))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return alphaNumSort(convertedData)
    }

    fun alphaNumSort(data: MutableList<String>): List<String> {
        return try {
            Collections.sort(
                data, AlphaNumComparator()
            )
            data
        } catch (e: Exception) {
            data.sort()
            data
        }
    }

}