package com.delarosa.detailsurprise.data.remote.endpoints

import com.delarosa.detailsurprise.data.remote.response.CompanyResponse
import com.delarosa.domain.Company
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CompanyService {
    @GET("company/")
    suspend fun getCompany(companyId: Int): Response<CompanyResponse>

    @POST("send_company/")
    suspend fun sendCompany(@Body company: Company): Response<Unit>
}
