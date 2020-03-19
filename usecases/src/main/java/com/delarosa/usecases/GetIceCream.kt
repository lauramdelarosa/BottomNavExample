package com.delarosa.usecases

import com.delarosa.data.ResultData
import com.delarosa.data.repository.IceCreamRepository
import com.delarosa.domain.IceCream

class GetIceCream(private val iceCreamRepository: IceCreamRepository) {
    suspend fun invoke(): ResultData<List<IceCream>> = iceCreamRepository.getIceCreams()
}