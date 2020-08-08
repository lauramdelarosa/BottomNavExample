package com.delarosa.data.datasource

interface LocalDataSource {

   suspend fun saveData(key: String, value: Any)

    suspend fun getData(key: String, type: Class<*>): Any?

    suspend fun remove(key: String)

    suspend  fun clear()
}
