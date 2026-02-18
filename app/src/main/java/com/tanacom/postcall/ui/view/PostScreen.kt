package com.tanacom.postcall.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.tanacom.postcall.ui.viewmodel.PostUiState
import com.tanacom.postcall.ui.viewmodel.PostViewModel


@Composable
fun PostScreen(postViewModel: PostViewModel, modifier: Modifier = Modifier) {

    // Observe the state from the ViewModel
    val uiState = postViewModel.state.collectAsState().value

    // Show the appropriate UI based on the state
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is PostUiState.Loading -> LoadingView()
            is PostUiState.Success -> PostList(posts = uiState.posts)
            is PostUiState.Error -> ErrorView(message = uiState.message)
        }
    }
}



