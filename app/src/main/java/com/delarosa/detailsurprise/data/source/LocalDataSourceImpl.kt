/*
 * Copyright (c) Evercheck 2018.
 */

package com.delarosa.detailsurprise.data.source

import android.content.SharedPreferences
import com.delarosa.data.datasource.LocalDataSource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : LocalDataSource {
    override suspend fun saveData(
        key: String,
        value: Any
    ) {
        withContext(Dispatchers.IO) {
            val editor = sharedPreferences.edit()
            when (value) {
                is String -> editor.putString(key, value)
                is Int -> editor.putInt(key, value)
                is Boolean -> editor.putBoolean(key, value)
                is Float -> editor.putFloat(key, value)
                is Long -> editor.putLong(key, value)
                else -> editor.putString(key, Gson().toJson(value))
            }
            editor.apply()
        }
    }

    override suspend fun getData(
        key: String,
        type: Class<*>
    ): Any? {
        return withContext(Dispatchers.IO) {
            when (type) {
                String::class.java -> sharedPreferences.getString(key, null)
                Int::class.java -> sharedPreferences.getInt(key, 0)
                Boolean::class.java -> sharedPreferences.getBoolean(
                    key, false
                )
                Float::class.java -> sharedPreferences.getFloat(key, 0f)
                Long::class.java -> sharedPreferences.getLong(key, 0L)
                else -> Gson().fromJson<Any?>(sharedPreferences.getString(key, null), type)
            }
        }
    }

    override suspend fun remove(key: String) {
        withContext(Dispatchers.IO) {
            if (sharedPreferences.contains(key)) {
                sharedPreferences.edit()
                    .remove(key)
                    .apply()
            }
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .clear()
                .apply()
        }
    }
}
