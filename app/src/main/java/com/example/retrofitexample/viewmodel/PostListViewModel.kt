package com.example.retrofitexample.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.model.network.RetrofitService
import com.example.retrofitexample.model.api.Post
import com.example.retrofitexample.model.database.PostDao
import com.example.retrofitexample.model.database.PostDatabase
import com.example.retrofitexample.model.repository.PostsRepository
import com.example.retrofitexample.utils.RecyclerViewItemClick
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class PostListViewModel(
    private var repository: PostsRepository
) : ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData

    private val _openDetail = MutableLiveData<Post>()
    val openDetail: LiveData<Post>
        get() = _openDetail

    init {
        getPostsCoroutine()
    }

    fun getPostsCoroutine() {
        launch {
            _liveData.value = State.ShowLoading
            val list = withContext(Dispatchers.IO) {
                try {
                    val result = repository.getPosts()
                    if(!result.isNullOrEmpty()){
                        repository.insertAll(result)
                    }
                    result
                } catch (e: Exception) {
                    repository.getAll()
                }

            }
            _liveData.value = State.Result(list)
            _liveData.value = State.HideLoading
        }
    }

    val recyclerViewItemClickListener = object : RecyclerViewItemClick {

        override fun itemClick(item: Post) {
            _openDetail.value = item
        }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val list: List<Post>?) : State()
    }

}