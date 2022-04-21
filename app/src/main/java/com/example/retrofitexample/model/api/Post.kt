package com.example.retrofitexample.model.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post_table")
data class Post(
    @PrimaryKey
    @SerializedName("id") val postId: Int?,
    val userId: String?,
    val title: String?,
    val body: String?
)
