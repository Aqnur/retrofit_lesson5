package com.example.retrofitexample.model.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitexample.model.api.Post
import com.example.retrofitexample.model.database.PostDao
import com.example.retrofitexample.model.network.RetrofitService
import retrofit2.HttpException
import java.io.IOException

const val POST_STARTING_PAGE_INDEX = 1

class PostsRepositoryImpl(
    private val api: RetrofitService,
    private val dao: PostDao
) : PostsRepository, PagingSource<Int, Post>() {

    override suspend fun getPosts(page: Int): List<Post> {
        return api.getPostApi().getPostListCoroutine(page).body()!!
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

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val position = params.key ?: POST_STARTING_PAGE_INDEX
            val response = api.getPostApi().getPostListCoroutine(position).body()!!
            val prevKey = if (position == POST_STARTING_PAGE_INDEX) null else position - 1
            val nextKey = if (response.isEmpty()) null else position + 1
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}