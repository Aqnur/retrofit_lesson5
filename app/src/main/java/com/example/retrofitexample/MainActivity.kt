package com.example.retrofitexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), PostAdapter.RecyclerViewItemClick, CoroutineScope {

    private lateinit var binding: ActivityMainBinding

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.swipeRefresh.setOnRefreshListener {
//            (binding.recyclerView.adapter as PostAdapter).clearAll()
            getPostsCoroutine()
        }

        binding.recyclerView.adapter =
            PostAdapter(itemClickListener = this@MainActivity)

        setAdapter()
        getPostsCoroutine()
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
    }

    override fun itemClick(position: Int, item: Post) {
        val intent = Intent(this, PostDetailActivity::class.java)
        intent.putExtra("post_id", item.postId)
        startActivity(intent)
    }

    private fun getPostsCoroutine() {
        launch {
            binding.swipeRefresh.isRefreshing = true
            val response = RetrofitService.getPostApi().getPostListCoroutine()
            if (response.isSuccessful) {
                (binding.recyclerView.adapter as PostAdapter).submitList(response.body())
                binding.swipeRefresh.isRefreshing = false
            } else {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}