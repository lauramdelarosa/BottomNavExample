package com.delarosa.data.repository

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.LocalDataSource
import com.delarosa.data.datasource.RemoteDetailSurpriseDataSource
import com.delarosa.domain.DetailSurprise

class DetailSurpriseRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDetailSurpriseDataSource: RemoteDetailSurpriseDataSource
) {

    suspend fun getMessage(key: String): String {
        return localDataSource.getData(key, String::class.java) as String
    }

    suspend fun getColor(key: String): String {
        return localDataSource.getData(key, String::class.java) as String
    }

    suspend fun getVideo(key: String): String {
        return localDataSource.getData(key, String::class.java) as String
    }

    suspend fun getCompanyId(key: String): Int {
        return localDataSource.getData(key, Int::class.java) as Int
    }

    suspend fun sendDetailSurprise(detailSurprise: DetailSurprise): ResultData<Unit> {
        return remoteDetailSurpriseDataSource.sendDetailSurprise(detailSurprise)
    }
}

