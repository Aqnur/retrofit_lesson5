package com.example.retrofitexample.model.repository

import com.example.retrofitexample.model.api.Post
import com.example.retrofitexample.model.database.PostDao
import com.example.retrofitexample.model.network.RetrofitService

class PostsRepositoryImpl(
    private val api: RetrofitService,
    private val dao: PostDao
) : PostsRepository {

    override suspend fun getPosts(): List<Post> {
        return api.getPostApi().getPostListCoroutine().body()!!
    }

    override suspend fun getPost(id: Int): Post {
        return api.getPostApi().getPostByIdCoroutine(id).body()!!
    }

    override fun insertAll(list: List<Post>) {
        return dao.insertAll(list)
    }

    override fun getAll(): List<Post> {
        return dao.getAll()
    }
}