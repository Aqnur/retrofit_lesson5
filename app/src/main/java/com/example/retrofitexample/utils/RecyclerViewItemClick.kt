package com.example.retrofitexample.utils

import com.example.retrofitexample.domain.model.api.Post

interface RecyclerViewItemClick {
    fun itemClick(item: Post)
}