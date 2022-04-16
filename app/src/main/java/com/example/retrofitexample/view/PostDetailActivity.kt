package com.example.retrofitexample.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexample.common.BaseActivity
import com.example.retrofitexample.databinding.ActivityPostDetailBinding
import com.example.retrofitexample.viewmodel.PostDetailViewModel

class PostDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initAndObserveViewModel()

        val postId = intent.getIntExtra("post_id", 1)
        viewModel.getPost(postId)
    }

    private fun initAndObserveViewModel() {
        viewModel = ViewModelProvider(this)[PostDetailViewModel::class.java]

        viewModel.liveData.observe(
            this,
            {
                binding.tvBody.text = it.body
                binding.tvTitle.text = it.title
            }
        )
    }
}