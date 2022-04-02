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

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.swipeRefresh.setOnRefreshListener {
            (binding.recyclerView.adapter as PostAdapter).clearAll()
            getPosts()
        }

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

    private fun getPosts() {
        binding.swipeRefresh.isRefreshing = true
        RetrofitService.getPostApi().getPostList().enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.d("My_post_list", response.body().toString())
                binding.swipeRefresh.isRefreshing = false
                if (response.isSuccessful) {
                    val list = response.body()
                    binding.recyclerView.adapter =
                        PostAdapter(list = list!!, itemClickListener = this@MainActivity)
                }
            }
        })
    }

    private fun getPostsCoroutine() {
        launch {
            binding.swipeRefresh.isRefreshing = true
            val response = RetrofitService.getPostApi().getPostListCoroutine()
            if (response.isSuccessful) {
                binding.recyclerView.adapter = PostAdapter(response.body(), itemClickListener = this@MainActivity)
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