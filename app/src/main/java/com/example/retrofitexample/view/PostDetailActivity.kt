package com.example.retrofitexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexample.RetrofitService
import com.example.retrofitexample.databinding.ActivityPostDetailBinding
import com.example.retrofitexample.model.Post
import com.example.retrofitexample.viewmodel.PostDetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetailActivity : AppCompatActivity() {

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