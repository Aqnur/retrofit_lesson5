package com.example.retrofitexample.model.api

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id") val postId: String?,
    val userId: String?,
    val title: String?,
    val body: String?
)
