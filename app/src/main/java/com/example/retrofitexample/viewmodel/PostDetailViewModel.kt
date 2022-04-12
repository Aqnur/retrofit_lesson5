package com.example.retrofitexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.RetrofitService
import com.example.retrofitexample.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostDetailViewModel: ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<Post>()
    val liveData: LiveData<Post>
        get() = _liveData

    fun getPost(id: Int) {
        launch {
            val response = RetrofitService.getPostApi().getPostByIdCoroutine(id)
            if (response.isSuccessful) {
                _liveData.postValue(response.body())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}