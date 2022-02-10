package com.kswafx.androidclean.data.remote

import com.google.gson.annotations.SerializedName
import com.kswafx.androidclean.domain.model.UserInfo

data class LoginResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("group")
    val group: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("facebook_id")
    val facebookId: String,
    @SerializedName("last_login")
    val lastLogin: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("tags")
    var tags: ArrayList<String> = arrayListOf()
)

fun LoginResponse.mapToDomain(): UserInfo {
    return UserInfo(
        userId = id,
        userName = userName,
        name = name,
        group = group,
        phone = phone,
        facebookId = facebookId,
        tags = tags
    )
}