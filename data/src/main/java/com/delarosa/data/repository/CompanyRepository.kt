package com.delarosa.data.repository

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteCompanyDataSource
import com.delarosa.domain.Company

class CompanyRepository(
    private val remoteCompanyDataSource: RemoteCompanyDataSource
) {
    suspend fun getCompany(idCompany: Int): ResultData<Company> {
        return remoteCompanyDataSource.getCompany(idCompany)
    }

    suspend fun sendCompany(company: Company): ResultData<Unit> {
        return remoteCompanyDataSource.sendCompany(company)
    }
}

