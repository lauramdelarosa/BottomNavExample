package com.delarosa.prueba.datasources

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteIceCreamDataSource
import com.delarosa.prueba.fakeListIceCream

class FakeRemoteDataSource : RemoteIceCreamDataSource {

    var remoteResponse = fakeListIceCream

    override suspend fun getAllIceCream() = ResultData.Success(remoteResponse)
}

