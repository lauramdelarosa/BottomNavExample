package com.delarosa.usecases

import com.delarosa.data.Constants
import com.delarosa.data.repository.DetailSurpriseRepository

class GetColor(private val detailSurpriseRepository: DetailSurpriseRepository) {
    suspend fun invoke(): String = detailSurpriseRepository.getMessage(Constants.COLOR)
}
