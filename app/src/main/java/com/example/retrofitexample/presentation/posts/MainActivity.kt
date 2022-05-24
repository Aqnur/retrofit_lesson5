package com.example.retrofitexample.presentation.posts

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.domain.common.BaseActivity
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.presentation.post_detail.PostDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelObserver: PostListViewModelObserver
    private val postListViewModel by viewModel<PostListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initAndObserveViewModel()

        binding.recyclerView.adapter =
            PostAdapter(itemClickListener = postListViewModel.recyclerViewItemClickListener)

        setAdapter()
        setBindings()
    }

    private fun setBindings() {
        binding.swipeRefresh.setOnRefreshListener {
            postListViewModel.getPostsCoroutine()
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
        viewModelObserver = PostListViewModelObserver(
            context = this,
            viewModel = postListViewModel,
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
            },
            showError = {
                binding.tvError.text = it.message
            }
        )

    }

}