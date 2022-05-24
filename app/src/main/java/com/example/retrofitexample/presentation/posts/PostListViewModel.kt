package com.example.retrofitexample.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.domain.common.UseCaseResponse
import com.example.retrofitexample.domain.model.ApiError
import com.example.retrofitexample.domain.model.api.Post
import com.example.retrofitexample.domain.usecases.GetPostsListUseCase
import com.example.retrofitexample.utils.RecyclerViewItemClick
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostListViewModel(
    private var useCase: GetPostsListUseCase
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

    private val _showError = MutableLiveData<ApiError>()
    val showError: LiveData<ApiError>
        get() = _showError

    init {
        getPostsCoroutine()
    }

    fun getPostsCoroutine() {
        useCase.invoke(viewModelScope, null, object : UseCaseResponse<List<Post>> {
            override fun onSuccess(result: List<Post>) {
                _liveData.value = State.Result(result)
            }

            override fun onError(apiError: ApiError?) {
                _showError.value = apiError
            }
        })

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