package com.tanacom.postcall.data.remote


import com.tanacom.postcall.data.model.Post

class FakeAPIService : APIService {

    override suspend fun getPosts(): List<Post> {
        return listOf(
            Post(
                userId = 1,
                id = 1,
                title = "Fake Post 1",
                body = "This is a fake post for testing purposes."
            ),
            Post(
                userId = 2,
                id = 2,
                title = "Fake Post 2",
                body = "Another fake post to preview the UI."
            )
        )
    }
}
