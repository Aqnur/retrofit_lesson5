package com.example.retrofitexample.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import com.example.retrofitexample.databinding.ItemPostBinding
import com.example.retrofitexample.model.Post

class PostAdapter(
    val itemClickListener: RecyclerViewItemClick? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Any>() {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is Post && newItem is Post -> {
                    oldItem.postId == newItem.postId
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is Post && newItem is Post -> {
                    oldItem as Post == newItem as Post
                }
                else -> false
            }
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Any>?) {
        differ.submitList(list)
    }

    companion object {
        const val VIEW_TYPE_POST = 0
        const val VIEW_TYPE_POST2 = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_POST -> {
                val binding: ItemPostBinding =
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_post,
                        parent,
                        false
                    )
                PostViewHolder(binding)
            }
            else -> {
                throw IllegalStateException("Incorrect ViewType found")
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_POST -> {
                val viewHolder = holder as PostViewHolder
                viewHolder.initContent(differ.currentList[position] as Post)
            }
        }
    }

    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initContent(data: Post?) {
            binding.data = data
            binding.executePendingBindings()

            binding.clMain.setOnClickListener {
                itemClickListener?.itemClick(adapterPosition, data!!)
            }
        }
    }

    inner class Post2ViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initContent(data: Post?) {
            binding.data = data
            binding.executePendingBindings()

            binding.clMain.setOnClickListener {
                itemClickListener?.itemClick(adapterPosition, data!!)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (differ.currentList[position]) {
            is Post -> VIEW_TYPE_POST
            else -> throw IllegalStateException("Incorrect ViewType found")
        }

    interface RecyclerViewItemClick {
        fun itemClick(position: Int, item: Post)
    }
}