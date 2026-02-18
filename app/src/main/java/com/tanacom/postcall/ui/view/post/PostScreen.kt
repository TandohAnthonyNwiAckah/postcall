package com.tanacom.postcall.ui.view.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.tanacom.postcall.ui.viewmodel.PostUiState
import com.tanacom.postcall.ui.viewmodel.PostViewModel

@Composable
fun PostScreen(
    postViewModel: PostViewModel, navController: NavController
) {
    val uiState = postViewModel.state.collectAsState().value

    when (uiState) {
        is PostUiState.Loading -> LoadingView()
        is PostUiState.Success -> PostList(
            posts = uiState.posts, onPostClick = { post ->
                // Navigate with postId
                navController.navigate("postDetail/${post.id}")
            })

        is PostUiState.Error -> ErrorView(message = uiState.message)
    }
}
