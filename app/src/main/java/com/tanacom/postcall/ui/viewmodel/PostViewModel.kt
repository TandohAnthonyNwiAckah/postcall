package com.tanacom.postcall.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanacom.postcall.data.model.Post
import com.tanacom.postcall.data.repository.PostRepository
import kotlinx.coroutines.launch


sealed class UiState {
    object Loading : UiState()
    data class Success(val posts: List<Post>) : UiState()
    data class Error(val message: String) : UiState()

}

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val internalState: MutableState<UiState> = mutableStateOf(UiState.Loading)

    val state: MutableState<UiState> = internalState


    init {
        fetchPosts()
    }


    private fun fetchPosts() {

        viewModelScope.launch {

            internalState.value = UiState.Loading

            try {
                val posts = repository.getPosts()
                internalState.value = UiState.Success(posts)
            } catch (e: Exception) {
                internalState.value = UiState.Error(e.message ?: "Unknown error")

            }
        }
    }


    fun getPostById(postId: Int): Post? {
        return when (val state = state.value) {
            is UiState.Success -> state.posts.find { it.id == postId }
            else -> null
        }
    }


}