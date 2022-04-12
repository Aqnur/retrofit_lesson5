package com.example.retrofitexample.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.model.Post
import com.example.retrofitexample.RetrofitService
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.viewmodel.PostListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PostListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initAndObserveViewModel()

        binding.recyclerView.adapter =
            PostAdapter(itemClickListener = viewModel.recyclerViewItemClickListener)

        setAdapter()
        setBindings()
    }

    private fun setBindings() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getPostsCoroutine()
        }
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
    }

    private fun initAndObserveViewModel() {
        viewModel = ViewModelProvider(this)[PostListViewModel::class.java]

        viewModel.liveData.observe(
            this,
            {
                when (it) {
                    is PostListViewModel.State.ShowLoading -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is PostListViewModel.State.HideLoading -> {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is PostListViewModel.State.Result -> {
                        (binding.recyclerView.adapter as PostAdapter).submitList(it.list)
                    }
                }
            }
        )

        viewModel.openDetail.observe(
            this,
            {
                val intent = Intent(this, PostDetailActivity::class.java)
                intent.putExtra("post_id", it.postId)
                startActivity(intent)
            }
        )

    }

}