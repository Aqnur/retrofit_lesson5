package com.example.retrofitexample

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id") val postId: Int?,
    val userId: String?,
    val title: String?,
    val body: String?
)
