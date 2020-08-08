package com.delarosa.data.datasource

import com.delarosa.data.ResultData
import com.delarosa.domain.Company
import com.delarosa.domain.DetailSurprise

interface RemoteAuthDataSource {
    suspend fun sendAuthCode(code: String): ResultData<DetailSurprise>
}
