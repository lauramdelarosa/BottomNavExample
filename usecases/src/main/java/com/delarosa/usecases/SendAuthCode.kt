package com.delarosa.usecases

import com.delarosa.data.ResultData
import com.delarosa.data.repository.AuthRepository
import com.delarosa.domain.DetailSurprise

class SendAuthCode(private val authRepository: AuthRepository) {
    suspend fun invoke(code: String): ResultData<Unit> = authRepository.sendAuthCode(code)
}
