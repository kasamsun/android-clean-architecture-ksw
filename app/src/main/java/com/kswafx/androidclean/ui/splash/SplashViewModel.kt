package com.kswafx.androidclean.ui.splash

import android.annotation.SuppressLint
import com.kswafx.androidclean.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableStateFlow<SplashActivityState>(SplashActivityState.Init)
    val state : StateFlow<SplashActivityState> get() = _state

    init {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            _state.value = SplashActivityState.IsFinish(true)
        }
    }

}

@SuppressLint("CustomSplashScreen")
sealed class SplashActivityState {
    object Init : SplashActivityState()
    data class IsFinish(val isFinish: Boolean) : SplashActivityState()
}
