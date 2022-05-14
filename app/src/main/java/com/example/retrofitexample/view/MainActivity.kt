package com.example.retrofitexample.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.common.BaseActivity
import com.example.retrofitexample.viewmodel.ViewModelProviderFactory
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.model.database.PostDao
import com.example.retrofitexample.model.database.PostDatabase
import com.example.retrofitexample.model.network.RetrofitService
import com.example.retrofitexample.model.repository.PostsRepository
import com.example.retrofitexample.model.repository.PostsRepositoryImpl
import com.example.retrofitexample.viewmodel.PostListViewModel
import com.example.retrofitexample.viewmodel.PostListViewModelObserver

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PostListViewModel
    private lateinit var viewModelObserver: PostListViewModelObserver

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
        val postDao: PostDao = PostDatabase.getDatabase(this).postDao()
        val postRepository: PostsRepository = PostsRepositoryImpl(RetrofitService, postDao)

        viewModel = PostListViewModel(postRepository)

        viewModelObserver = PostListViewModelObserver(
            context = this,
            viewModel = viewModel,
            viewLifecycleOwner = this,
            liveData = {
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
            },
            openDetail = {
                val intent = Intent(this, PostDetailActivity::class.java)
                intent.putExtra("post_id", it.postId)
                startActivity(intent)
            }
        )

    }

}