package com.kswafx.androidclean.ui.main

import androidx.lifecycle.MutableLiveData
import com.kswafx.androidclean.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel() {

    val userName = MutableLiveData("")

    fun init(userName: String?) {
        this.userName.value = userName
    }
}