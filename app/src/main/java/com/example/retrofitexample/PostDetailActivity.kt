package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.databinding.ActivityPostDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val postId = intent.getIntExtra("post_id", 1)
        getPost(id = postId)
    }

    private fun getPost(id: Int) {
        RetrofitService.getPostApi().getPostById(id).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                binding.progressBar.visibility = View.GONE
                val post = response.body()
                if (post != null) {
                    binding.tvBody.text = post.body
                    binding.tvTitle.text = post.title
                }
            }
        })
    }
}