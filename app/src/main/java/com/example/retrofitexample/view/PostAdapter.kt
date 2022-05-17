package com.example.retrofitexample.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import com.example.retrofitexample.databinding.ItemPostBinding
import com.example.retrofitexample.model.api.Post
import com.example.retrofitexample.utils.RecyclerViewItemClick

class PostAdapter(
    val itemClickListener: RecyclerViewItemClick? = null
) : PagingDataAdapter<Post, PostAdapter.PostViewHolder>(PostComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPostBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_post,
                parent,
                false
            )
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initContent(data: Post?) {
            binding.data = data
            binding.recyclerViewItemClickListener = itemClickListener
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.initContent(item)
        }
    }
}