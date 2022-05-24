package com.example.retrofitexample.domain.repository

import com.example.retrofitexample.domain.model.api.Post

interface PostsRepository {

    suspend fun getPosts(): List<Post>

    suspend fun getPost(id: Int): Post

    fun insertAll(list: List<Post>)

    fun getAll(): List<Post>

}