package com.tanacom.postcall.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanacom.postcall.data.model.Post
import com.tanacom.postcall.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PostUiState {
    object Loading : PostUiState()
    data class Success(val posts: List<Post>) : PostUiState()
    data class Error(val message: String) : PostUiState()

}


@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    //   private val repository = PostRepository()

    private val internalState = MutableStateFlow<PostUiState>(PostUiState.Loading)

    val state: StateFlow<PostUiState> = internalState.asStateFlow()


    init {
        fetchPosts()
    }

    private fun fetchPosts() {

        viewModelScope.launch {

            internalState.value = PostUiState.Loading

            try {
                val posts = repository.getPosts()
                internalState.value = PostUiState.Success(posts)
            } catch (e: Exception) {
                internalState.value = PostUiState.Error(e.message ?: "Unknown error")

            }
        }
    }

}