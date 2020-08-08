package com.delarosa.detailsurprise.data.source

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteAuthDataSource
import com.delarosa.detailsurprise.data.callServices
import com.delarosa.detailsurprise.data.mappers.toDomainDetail
import com.delarosa.detailsurprise.data.remote.endpoints.AuthService
import com.delarosa.detailsurprise.data.remote.response.DetailSurpriseRemote
import com.delarosa.detailsurprise.data.safeApiCall
import com.delarosa.domain.DetailSurprise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteAuthAuthDataSourceImpl(private val authService: AuthService) : RemoteAuthDataSource {
    override suspend fun sendAuthCode(code: String): ResultData<DetailSurprise> =
        withContext(Dispatchers.IO) {
            if (code == "8888") {
                ResultData.Success(
                    DetailSurprise(
                        "8888",
                        "Hola aleja, Fabian tiene una sorpresa para ti!! esperamos que la disfrutes",
                        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        "blue",
                        1
                    )
                )
            } else {
                safeApiCall(
                    call = { renderData(authService.sendAuthCode(code).callServices()) },
                    errorMessage = "Something failed when the service have been called '../authCode'"
                )
            }
        }

    private fun renderData(resultData: ResultData<DetailSurpriseRemote>): ResultData<DetailSurprise> =
        when (resultData) {
            is ResultData.Success -> ResultData.Success(resultData.data.toDomainDetail())
            is ResultData.Error -> resultData
        }
}
