package com.tanacom.postcall

import retrofit2.http.GET


interface APIService {

    @GET("posts")
    suspend fun getPosts(): List<Post>


}