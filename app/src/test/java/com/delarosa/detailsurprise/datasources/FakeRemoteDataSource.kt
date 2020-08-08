package com.delarosa.detailsurprise.datasources

import com.delarosa.data.ResultData
import com.delarosa.data.datasource.RemoteIceCreamDataSource
import com.delarosa.detailsurprise.fakeListIceCream

class FakeRemoteDataSource : RemoteIceCreamDataSource {

    var remoteResponse = fakeListIceCream

    override suspend fun getAllIceCream() = ResultData.Success(remoteResponse)
}

