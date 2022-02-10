package com.kswafx.androidclean.common

data class Failure(
    val code: Int,
    val error: Detail
) {
    data class Detail(val message: String)
    val message : String get() = error.message
}
