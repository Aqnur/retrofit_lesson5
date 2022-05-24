package com.example.retrofitexample.presentation.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.domain.common.UseCaseResponse
import com.example.retrofitexample.domain.model.ApiError
import com.example.retrofitexample.domain.model.api.Post
import com.example.retrofitexample.domain.usecases.GetPostDetailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class PostDetailViewModel(private val useCase: GetPostDetailUseCase) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<Post>()
    val liveData: LiveData<Post>
        get() = _liveData

    fun getPost(id: Int) {
        useCase.invoke(viewModelScope, id, object : UseCaseResponse<Post> {
            override fun onSuccess(result: Post) {
                _liveData.value = result
            }

            override fun onError(apiError: ApiError?) {
                //todo error handler
            }

        })
    }

    val titleFieldText = MutableLiveData<String>()
    val descriptionFieldText = MutableLiveData<String>()

    fun onSuccess(post: Post?) {
        post?.let {
            titleFieldText.value = it.title
            descriptionFieldText.value = it.body
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}