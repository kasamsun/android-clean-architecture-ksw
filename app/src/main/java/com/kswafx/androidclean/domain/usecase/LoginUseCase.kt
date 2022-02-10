package com.kswafx.androidclean.domain.usecase

import com.kswafx.androidclean.common.BaseResult
import com.kswafx.androidclean.common.Failure
import com.kswafx.androidclean.domain.model.UserInfo
import com.kswafx.androidclean.domain.repository.StaffRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val staffRepository: StaffRepository
) {
    suspend fun invoke(userName: String, password: String) : Flow<BaseResult<UserInfo, Failure>> {
        return staffRepository.login(userName, password)
    }

    data class Params(
        val userName: String,
        val userPassword: String
    )
}
