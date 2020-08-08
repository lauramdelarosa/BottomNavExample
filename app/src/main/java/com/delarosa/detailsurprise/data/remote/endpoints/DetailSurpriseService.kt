package com.delarosa.detailsurprise.data.remote.endpoints

import com.delarosa.domain.DetailSurprise
import retrofit2.Response
import retrofit2.http.GET

interface DetailSurpriseService {
    @GET("detail/")
    suspend fun sendDetail(detailSurprise: DetailSurprise): Response<Unit>
}
