package com.delarosa.data.repository

import com.delarosa.data.Constants.Companion.COLOR
import com.delarosa.data.Constants.Companion.COMPANY_ID
import com.delarosa.data.Constants.Companion.MESSAGE
import com.delarosa.data.Constants.Companion.VIDEO
import com.delarosa.data.ResultData
import com.delarosa.data.datasource.LocalDataSource
import com.delarosa.data.datasource.RemoteAuthDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun sendAuthCode(code: String): ResultData<Unit> {
        when (val result = remoteAuthDataSource.sendAuthCode(code)) {
            is ResultData.Success -> {
                localDataSource.run {
                    saveData(COLOR, result.data.color)
                    saveData(VIDEO, result.data.video)
                    saveData(MESSAGE, result.data.message)
                    saveData(COMPANY_ID, result.data.companyId)
                }
            }
            is ResultData.Error -> return result
        }

        return ResultData.Success(Unit)
    }
}

