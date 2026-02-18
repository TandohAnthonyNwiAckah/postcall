package com.tanacom.postcall.data.remote

import com.tanacom.postcall.data.model.Post
import retrofit2.http.GET

interface APIService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

}