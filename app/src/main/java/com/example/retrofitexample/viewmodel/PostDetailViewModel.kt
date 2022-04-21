package com.example.retrofitexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitexample.model.RetrofitService
import com.example.retrofitexample.model.api.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostDetailViewModel(private val app: Application) : AndroidViewModel(app), CoroutineScope {

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