package com.tanacom.postcall.data.repository

import com.tanacom.postcall.data.model.Post
import com.tanacom.postcall.data.remote.RetrofitInstance

class PostRepository {

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}