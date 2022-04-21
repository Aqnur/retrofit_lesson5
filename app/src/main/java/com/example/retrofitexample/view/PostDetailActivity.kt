package com.example.retrofitexample.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexample.R
import com.example.retrofitexample.common.BaseActivity
import com.example.retrofitexample.databinding.ActivityPostDetailBinding
import com.example.retrofitexample.viewmodel.PostDetailViewModel

class PostDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_post_detail
        )
        binding.lifecycleOwner = this
        val view = binding.root

        setContentView(view)

        initAndObserveViewModel()
        binding.viewModel = viewModel

        val postId = intent.getIntExtra("post_id", 1)
        viewModel.getPost(postId)
    }

    private fun initAndObserveViewModel() {
        viewModel = ViewModelProvider(this)[PostDetailViewModel::class.java]

        viewModel.liveData.observe(
            this,
            {
                viewModel.onSuccess(it)
            }
        )
    }
}