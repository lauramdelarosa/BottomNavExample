package com.delarosa.data.datasource

import com.delarosa.data.ResultData
import com.delarosa.domain.DetailSurprise

interface RemoteDetailSurpriseDataSource {
    suspend fun sendDetailSurprise (detailSurprise: DetailSurprise): ResultData<Unit>
}
