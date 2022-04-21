package com.example.retrofitexample.model.network

import com.example.retrofitexample.model.api.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("posts")
    fun getPostList(): Call<List<Post>>

    @GET("posts")
    suspend fun getPostListCoroutine(): Response<List<Post>>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>

    @GET("posts/{id}")
    suspend fun getPostByIdCoroutine(@Path("id") id: Int): Response<Post>
}