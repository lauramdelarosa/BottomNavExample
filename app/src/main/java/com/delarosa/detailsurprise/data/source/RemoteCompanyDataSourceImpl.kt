package com.delarosa.detailsurprise.data.source

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteCompanyDataSource
import com.delarosa.detailsurprise.data.callServices
import com.delarosa.detailsurprise.data.mappers.toDomainCompany
import com.delarosa.detailsurprise.data.remote.endpoints.CompanyService
import com.delarosa.detailsurprise.data.remote.response.CompanyResponse
import com.delarosa.detailsurprise.data.safeApiCall
import com.delarosa.domain.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteCompanyDataSourceImpl(private val companyService: CompanyService) :
    RemoteCompanyDataSource {

    override suspend fun getCompany(companyId: Int): ResultData<Company> =
        withContext(Dispatchers.IO) {
            if (companyId == 1) {
                ResultData.Success(
                    Company(
                        "Esperamos que te haya gustado esta linda sorpresa, Puedes encontrar mas información acerca de nosotros en:",
                        "none",
                        "wa.link/ipp6pr",
                        "https://www.instagram.com/henrycavill/",
                        "",
                        "https://www.facebook.com/parroquiasan.alfonsomariadeligorio.98",
                        ""
                    )
                )
            } else {
                safeApiCall(
                    call = { renderData(companyService.getCompany(companyId).callServices()) },
                    errorMessage = "Something failed when the service have been called '../getCompany'"
                )
            }
        }


    override suspend fun sendCompany(company: Company): ResultData<Unit> {
        return withContext(Dispatchers.IO) {
            safeApiCall(
                call = { companyService.sendCompany(company).callServices() },
                errorMessage = "Something failed when the service have been called '../getCompany'"
            )
        }
    }

    private fun renderData(resultData: ResultData<CompanyResponse>): ResultData<Company> =
        when (resultData) {
            is ResultData.Success -> ResultData.Success(resultData.data.toDomainCompany())
            is ResultData.Error -> resultData
        }
}
