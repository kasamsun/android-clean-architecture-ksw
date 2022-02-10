package com.kswafx.androidclean.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.kswafx.androidclean.R
import com.kswafx.androidclean.common.BaseActivity
import com.kswafx.androidclean.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun layoutId() = R.layout.main_activity

    override fun viewModel() = mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.init(this.intent.getStringExtra("userName"))
    }
}