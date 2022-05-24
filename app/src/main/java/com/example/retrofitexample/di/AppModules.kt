package com.example.retrofitexample.di

import android.content.Context
import com.example.retrofitexample.data.database.PostDao
import com.example.retrofitexample.data.database.PostDatabase
import com.example.retrofitexample.data.network.RetrofitService
import com.example.retrofitexample.domain.repository.PostsRepository
import com.example.retrofitexample.domain.repository.PostsRepositoryImpl
import com.example.retrofitexample.domain.usecases.GetPostDetailUseCase
import com.example.retrofitexample.domain.usecases.GetPostsListUseCase
import com.example.retrofitexample.presentation.post_detail.PostDetailViewModel
import com.example.retrofitexample.presentation.posts.PostListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val networkModule = module {
    single { getRetrofitService() }
}

val daoModule = module {
    single { getPostDao(context = get()) }
}

val useCaseModule = module {
    single { GetPostsListUseCase(repository = get()) }
    single { GetPostDetailUseCase(repository = get()) }
}

val repositoryModule = module {
    single<PostsRepository> { PostsRepositoryImpl(api = get(), dao = get()) }
}

val viewModelModule = module {
    viewModel { PostListViewModel(useCase = get()) }
    viewModel { PostDetailViewModel(useCase = get()) }
}

val appModule = networkModule + daoModule + repositoryModule + viewModelModule + useCaseModule

private fun getRetrofitService(): RetrofitService = RetrofitService
private fun getPostDao(context: Context): PostDao = PostDatabase.getDatabase(context).postDao()