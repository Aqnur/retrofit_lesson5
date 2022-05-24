package com.example.retrofitexample.domain.usecases

import com.example.retrofitexample.domain.common.BaseUseCase
import com.example.retrofitexample.domain.model.api.Post
import com.example.retrofitexample.domain.repository.PostsRepository

class GetPostsListUseCase(private val repository: PostsRepository) :
    BaseUseCase<List<Post>, Any?>() {

    override suspend fun run(params: Any?): List<Post> {
        return repository.getPosts()
    }

}