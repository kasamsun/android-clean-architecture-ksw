package com.kswafx.androidclean.data.repository

import com.kswafx.androidclean.common.BaseResult
import com.kswafx.androidclean.common.Failure
import com.kswafx.androidclean.data.remote.WemoService
import com.kswafx.androidclean.domain.model.UserInfo
import com.kswafx.androidclean.domain.repository.StaffRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val wemoService: WemoService
) : StaffRepository {

    override suspend fun login(userName: String, password: String): Flow<BaseResult<UserInfo, Failure>> {
        return flow {
            val userInfo = wemoService.login(userName, password)
            emit(userInfo)
        }
    }

}
