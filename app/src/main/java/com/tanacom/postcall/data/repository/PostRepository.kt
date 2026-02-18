package com.tanacom.postcall.data.repository

import com.tanacom.postcall.data.model.Post
import com.tanacom.postcall.data.remote.APIService
import javax.inject.Inject
import javax.inject.Singleton


//import com.tanacom.postcall.data.remote.RetrofitInstance

//class PostRepository{
//
//    suspend fun getPosts(): List<Post> {
//        return RetrofitInstance.api.getPosts()
//    }
//}


@Singleton
class PostRepository @Inject constructor(private val apiService: APIService) {

    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}
