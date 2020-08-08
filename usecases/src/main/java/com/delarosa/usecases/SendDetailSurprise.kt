package com.delarosa.usecases

import com.delarosa.data.ResultData
import com.delarosa.data.repository.DetailSurpriseRepository
import com.delarosa.domain.DetailSurprise

class SendDetailSurprise(private val detailSurpriseRepository: DetailSurpriseRepository) {
    suspend fun invoke(detailSurprise: DetailSurprise): ResultData<Unit> =
        detailSurpriseRepository.sendDetailSurprise(detailSurprise)
}
