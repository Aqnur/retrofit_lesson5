package com.example.retrofitexample.presentation.posts

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.retrofitexample.domain.model.ApiError
import com.example.retrofitexample.domain.model.api.Post

class PostListViewModelObserver(
    private val context: Context,
    private val viewModel: PostListViewModel,
    private val viewLifecycleOwner: LifecycleOwner,

    private val showError: ((apiError: ApiError) -> Unit),
    private val openDetail: ((post: Post) -> Unit),
    private val liveData: ((state: PostListViewModel.State) -> Unit)
) {

    init {
        observeViewModel()
    }

    private fun observeViewModel() {
        liveData.apply {
            viewModel.liveData.observe(
                viewLifecycleOwner
            ) {
                this.invoke(it)
            }
        }

        showError.apply {
            viewModel.showError.observe(
                viewLifecycleOwner
            ) {
                this.invoke(it)
            }
        }

        openDetail.apply {
            viewModel.openDetail.observe(
                viewLifecycleOwner
            ) {
                this.invoke(it)
            }
        }
    }

}