package com.delarosa.detailsurprise.data.source

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteDetailSurpriseDataSource
import com.delarosa.detailsurprise.data.callServices
import com.delarosa.detailsurprise.data.remote.endpoints.DetailSurpriseService
import com.delarosa.detailsurprise.data.safeApiCall
import com.delarosa.domain.DetailSurprise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDetailSurpriseDataSourceImpl(private val detailSurpriseService: DetailSurpriseService) :
    RemoteDetailSurpriseDataSource {

    override suspend fun sendDetailSurprise(detailSurprise: DetailSurprise): ResultData<Unit> {
        return withContext(Dispatchers.IO) {
            safeApiCall(
                call = { detailSurpriseService.sendDetail(detailSurprise).callServices() },
                errorMessage = "Something failed when the service have been called '../sendDetail'"
            )
        }
    }
}
