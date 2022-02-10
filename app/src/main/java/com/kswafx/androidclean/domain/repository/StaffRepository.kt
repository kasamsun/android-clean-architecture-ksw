package com.kswafx.androidclean.domain.repository

import com.kswafx.androidclean.common.BaseResult
import com.kswafx.androidclean.common.Failure
import com.kswafx.androidclean.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface StaffRepository {
    suspend fun login(userName: String, password: String) : Flow<BaseResult<UserInfo, Failure>>
}
