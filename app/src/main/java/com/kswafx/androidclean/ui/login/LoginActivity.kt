package com.kswafx.androidclean.ui.login

import android.content.Intent
import android.media.ToneGenerator
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kswafx.androidclean.R
import com.kswafx.androidclean.common.BaseActivity
import com.kswafx.androidclean.databinding.LoginActivityBinding
import com.kswafx.androidclean.ui.main.MainActivity
import com.kswafx.iamalertdialog.IamAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginActivityBinding, LoginViewModel>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun layoutId() = R.layout.login_activity

    override fun viewModel() = loginViewModel

    @Inject
    lateinit var toneGenerator: ToneGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        // make activity full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        super.onCreate(savedInstanceState)

        observe()
        openActivityAnimation()
    }

    private fun observe() {
        viewModel().event.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleEvent(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleEvent(event : LoginActivityEvent) {
        when(event) {
            is LoginActivityEvent.PushPin -> toneGenerator.run {
                startTone(ToneGenerator.TONE_PROP_BEEP)
            }.also {
                hideKeyboard()
            }
            is LoginActivityEvent.ShowError -> {
                IamAlertDialog(this, IamAlertDialog.ERROR_TYPE)
                    .setContentText(event.message)
                    .show()
            }
            is LoginActivityEvent.LoginComplete -> {
                openMainActivity(event.userInfo.name)
            }
        }
    }

    private fun hideKeyboard() {
        this.currentFocus?.let {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun openActivityAnimation() {
        binding?.let {
            val anim1 = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.fade_in)
            anim1.startOffset = 100
            it.viewBackground.startAnimation(anim1)

            val anim2 = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.item_animation_from_bottom)
            anim2.startOffset = 500
            it.card.startAnimation(anim2)

            val anim3 = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.item_animation_from_bottom)
            anim3.startOffset = 500
            anim3.duration = 1000
            it.layoutVersion.startAnimation(anim3)
        }
    }

    private fun openMainActivity(userName: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
    }

}