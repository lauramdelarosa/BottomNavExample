package com.delarosa.usecases

import com.delarosa.data.ResultData
import com.delarosa.data.repository.CompanyRepository
import com.delarosa.domain.Company

class SendCompany(private val companyRepository: CompanyRepository) {
    suspend fun invoke(company: Company): ResultData<Unit> = companyRepository.sendCompany(company)
}
