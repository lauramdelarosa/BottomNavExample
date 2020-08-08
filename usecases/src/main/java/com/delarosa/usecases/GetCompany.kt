package com.delarosa.usecases

import com.delarosa.data.Constants
import com.delarosa.data.ResultData
import com.delarosa.data.repository.CompanyRepository
import com.delarosa.data.repository.DetailSurpriseRepository
import com.delarosa.domain.Company

class GetCompany(
    private val detailSurpriseRepository: DetailSurpriseRepository,
    private val companyRepository: CompanyRepository
) {
    suspend fun invoke(): ResultData<Company> {
        val companyId = detailSurpriseRepository.getCompanyId(Constants.COMPANY_ID)
        return companyRepository.getCompany(companyId)
    }
}
