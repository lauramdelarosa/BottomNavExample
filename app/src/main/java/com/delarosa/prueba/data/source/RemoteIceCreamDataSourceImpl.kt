package com.delarosa.prueba.data.source

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteIceCreamDataSource
import com.delarosa.domain.IceCream
import com.delarosa.prueba.data.callServices
import com.delarosa.prueba.data.endpoints.IceCreamService
import com.delarosa.prueba.data.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteIceCreamDataSourceImpl(private val iceCreamService: IceCreamService) : RemoteIceCreamDataSource {
    override suspend fun getAllIceCream(): ResultData<List<IceCream>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { iceCreamService.getIceCreams().callServices() },
            errorMessage = "Something failed when the service have been called '../products'"
        )
    }
}