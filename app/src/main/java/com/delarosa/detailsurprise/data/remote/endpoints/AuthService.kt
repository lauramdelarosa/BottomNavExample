package com.delarosa.detailsurprise.data.remote.endpoints

import com.delarosa.detailsurprise.data.remote.response.DetailSurpriseRemote
import retrofit2.Response
import retrofit2.http.POST

interface AuthService {
    @POST("auth_code/")
    suspend fun sendAuthCode(code: String): Response<DetailSurpriseRemote>
}
