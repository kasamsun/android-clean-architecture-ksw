package com.kswafx.androidclean.common

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int
}
