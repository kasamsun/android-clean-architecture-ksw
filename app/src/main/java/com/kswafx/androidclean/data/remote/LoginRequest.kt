package com.kswafx.androidclean.data.remote

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_password")
    val userPassword: String
)
