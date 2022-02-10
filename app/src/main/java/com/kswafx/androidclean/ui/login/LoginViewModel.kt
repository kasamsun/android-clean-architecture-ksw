package com.kswafx.androidclean.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kswafx.androidclean.BuildConfig
import com.kswafx.androidclean.common.BaseResult
import com.kswafx.androidclean.common.BaseViewModel
import com.kswafx.androidclean.domain.model.UserInfo
import com.kswafx.androidclean.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val isLoading = MutableLiveData(false)
    val userName = MutableLiveData("")
    private val pin = MutableLiveData("")
    val pinCount = MutableLiveData(0)
    val flavorName = MutableLiveData(BuildConfig.FLAVOR)
    val versionName = MutableLiveData(BuildConfig.VERSION_NAME)

    private val _channel = Channel<LoginActivityEvent>(Channel.CONFLATED)
    val event = _channel.receiveAsFlow()

    fun pushPin(pinNumber: Int) {
        if (pin.value?.length!! < 4) {
            pin.value = pin.value + pinNumber
        }
        pinCount.value = pin.value!!.length
        if (pinCount.value == 4) {
            login(userName.value!!, pin.value!!)
        }
        viewModelScope.launch { _channel.send(LoginActivityEvent.PushPin(pinNumber)) }
    }

    fun popPin() {
        if (pin.value?.length!! > 0) {
            pin.value = pin.value!!.substring(0, pin.value!!.length-1)
        }
        pinCount.value = pin.value!!.length
    }

    fun clearPin() {
        pin.value = ""
        pinCount.value = 0
    }

    fun login(userName: String, password: String) {

        if (userName.isEmpty()) {
            viewModelScope.launch { _channel.send(LoginActivityEvent.ShowError("Please enter user name")) }
            clearPin()
            return
        }
        if (password.isEmpty()) {
            viewModelScope.launch { _channel.send(LoginActivityEvent.ShowError("Please enter password")) }
            clearPin()
            return
        }

        viewModelScope.launch {
            loginUseCase.invoke(userName, password)
                .onStart {
                    isLoading.value = true
                }
                .catch { e ->
                    Log.e("KSW", e.message.toString())
                    isLoading.value = false
                    clearPin()
                }
                .collect { result ->
                    isLoading.value = false
                    clearPin()
                    when (result) {
                        is BaseResult.Success -> {
                            viewModelScope.launch { _channel.send(LoginActivityEvent.LoginComplete(result.data)) }
                        }
                        is BaseResult.Error -> {
                            viewModelScope.launch { _channel.send(LoginActivityEvent.ShowError(result.err.message)) }
                        }
                    }
                }
        }
    }
}

sealed class LoginActivityEvent {
    data class PushPin(val pinNumber: Int): LoginActivityEvent()
    data class ShowError(val message: String) : LoginActivityEvent()
    data class LoginComplete(val userInfo: UserInfo) : LoginActivityEvent()
}