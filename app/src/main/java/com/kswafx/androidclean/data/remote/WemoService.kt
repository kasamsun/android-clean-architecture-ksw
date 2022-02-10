package com.kswafx.androidclean.data.remote

import com.kswafx.androidclean.common.BaseResult
import com.kswafx.androidclean.common.Failure
import com.kswafx.androidclean.common.NoInternetConnectionException
import com.kswafx.androidclean.domain.model.UserInfo
import org.json.JSONObject

class WemoService constructor(private val wemoApi: WemoApi) {
    suspend fun login(userName: String, password: String) : BaseResult<UserInfo, Failure> {
        return try {
            val response = wemoApi.login(LoginRequest(userName, password))
            if (response.isSuccessful) {
                val result = response.body()
                BaseResult.Success(result!!.mapToDomain())
            } else {
                val json = JSONObject(response.errorBody()!!.charStream().readText())
                val message = json.getJSONObject("error").getString("message")
                BaseResult.Error(Failure(response.code(), Failure.Detail(message)))
            }
        } catch (e: NoInternetConnectionException){
            BaseResult.Error(Failure(0, Failure.Detail(e.message)))
        } catch (e: Exception){
            BaseResult.Error(Failure(-1, Failure.Detail(e.message.toString())))
        }
    }
}