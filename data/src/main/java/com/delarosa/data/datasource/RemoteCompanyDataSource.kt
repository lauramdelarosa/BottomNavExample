package com.delarosa.data.datasource

import com.delarosa.data.ResultData
import com.delarosa.domain.Company
import com.delarosa.domain.DetailSurprise

interface RemoteCompanyDataSource {
    suspend fun getCompany(companyId: Int): ResultData<Company>
    suspend fun sendCompany(company: Company): ResultData<Unit>
}
