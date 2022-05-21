package com.example.retrofitexample.di

import android.content.Context
import com.example.retrofitexample.model.database.PostDao
import com.example.retrofitexample.model.database.PostDatabase
import com.example.retrofitexample.model.network.RetrofitService
import com.example.retrofitexample.model.repository.PostsRepository
import com.example.retrofitexample.model.repository.PostsRepositoryImpl
import com.example.retrofitexample.viewmodel.PostDetailViewModel
import com.example.retrofitexample.viewmodel.PostListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val networkModule = module {
    single { getRetrofitService() }
}

val daoModule = module {
    single { getPostDao(context = get()) }
}

val repositoryModule = module {
    single<PostsRepository> { PostsRepositoryImpl(api = get(), dao = get()) }
}

val viewModelModule = module {
    viewModel { PostListViewModel(repository = get()) }
    viewModel { PostDetailViewModel(app = get()) }
}

val appModule = networkModule + daoModule + repositoryModule + viewModelModule

private fun getRetrofitService(): RetrofitService = RetrofitService
private fun getPostDao(context: Context): PostDao = PostDatabase.getDatabase(context).postDao()
