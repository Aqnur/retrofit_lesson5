package com.example.retrofitexample.model.repository

import com.example.retrofitexample.model.api.Post

interface PostsRepository {

    suspend fun getPosts(page: Int): List<Post>

    suspend fun getPost(id: Int): Post

    fun insertAll(list: List<Post>)

    fun getAll(): List<Post>

}