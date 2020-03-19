package com.delarosa.data.repository

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteIceCreamDataSource
import com.delarosa.domain.IceCream

class IceCreamRepository(private val iceCreamDataSource: RemoteIceCreamDataSource) {
    suspend fun getIceCreams(): ResultData<List<IceCream>> = iceCreamDataSource.getAllIceCream()
}

