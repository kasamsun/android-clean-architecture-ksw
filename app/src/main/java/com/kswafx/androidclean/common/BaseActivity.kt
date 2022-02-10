package com.kswafx.androidclean.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kswafx.androidclean.BR

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    abstract fun layoutId(): Int
    abstract fun viewModel(): V?

    val viewModel : V? get() = mViewModel
    val binding : T? get() = mViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = if (mViewModel == null) viewModel() else mViewModel

        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        mViewDataBinding?.apply {
            this.setVariable(BR.viewModel, viewModel())
            this.executePendingBindings()
            this.lifecycleOwner = this@BaseActivity
        }
    }
}
