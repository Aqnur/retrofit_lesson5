package com.example.retrofitexample.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.model.RetrofitService
import com.example.retrofitexample.model.api.Post
import com.example.retrofitexample.view.PostAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostListViewModel(
    private val context: Context
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
            val response = RetrofitService.getPostApi().getPostListCoroutine()
            if (response.isSuccessful) {
                _liveData.value = State.Result(response.body())
                _liveData.value = State.HideLoading
            }
        }
    }

    val recyclerViewItemClickListener = object : PostAdapter.RecyclerViewItemClick {

        override fun itemClick(position: Int, item: Post) {
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