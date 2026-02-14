package com.tanacom.postcall

class PostRepository {

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}