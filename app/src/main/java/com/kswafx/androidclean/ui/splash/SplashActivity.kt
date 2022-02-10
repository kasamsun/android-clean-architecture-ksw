package com.kswafx.androidclean.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kswafx.androidclean.R
import com.kswafx.androidclean.common.BaseActivity
import com.kswafx.androidclean.databinding.SplashActivityBinding
import com.kswafx.androidclean.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashActivityBinding, SplashViewModel>() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun layoutId() = R.layout.splash_activity

    override fun viewModel() = splashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding?.rootLayout?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))

        observe()
    }

    private fun observe() {
        viewModel().state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleState(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(finishState : SplashActivityState) {
        when(finishState) {
            is SplashActivityState.IsFinish -> openLoginActivity()
            else -> {}
        }
    }

    private fun openLoginActivity() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
