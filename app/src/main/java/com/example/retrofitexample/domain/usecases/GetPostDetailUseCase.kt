package com.example.retrofitexample.domain.usecases

import com.example.retrofitexample.domain.common.BaseUseCase
import com.example.retrofitexample.domain.model.api.Post
import com.example.retrofitexample.domain.repository.PostsRepository

class GetPostDetailUseCase(
    private val repository: PostsRepository
) : BaseUseCase<Post, Int>() {

    override suspend fun run(params: Int?): Post {
        return repository.getPost(params!!)
    }

}