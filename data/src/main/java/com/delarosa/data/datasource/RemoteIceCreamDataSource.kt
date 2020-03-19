package com.delarosa.data.datasource

import com.delarosa.data.ResultData
import com.delarosa.domain.IceCream

interface RemoteIceCreamDataSource {
    suspend fun getAllIceCream(): ResultData<List<IceCream>>
}