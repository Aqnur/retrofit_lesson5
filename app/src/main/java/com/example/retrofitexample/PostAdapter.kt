package com.example.retrofitexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.databinding.ItemPostBinding

class PostAdapter(
    var list: List<Post>? = null,
    val itemClickListener: RecyclerViewItemClick? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as PostViewHolder
        viewHolder.initContent(list?.get(position))
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

    fun clearAll() {
        (list as? ArrayList<Post>)?.clear()
        notifyDataSetChanged()
    }

    interface RecyclerViewItemClick {

        fun itemClick(position: Int, item: Post)
    }
}