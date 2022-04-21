package com.example.retrofitexample.utils

import com.example.retrofitexample.model.api.Post

interface RecyclerViewItemClick {
    fun itemClick(item: Post)
}