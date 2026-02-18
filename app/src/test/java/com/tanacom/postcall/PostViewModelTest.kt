package com.tanacom.postcall

import com.tanacom.postcall.data.remote.FakeAPIService
import com.tanacom.postcall.data.repository.PostRepository
import com.tanacom.postcall.ui.viewmodel.PostUiState
import com.tanacom.postcall.ui.viewmodel.PostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class PostViewModelTest {

    private val fakeRepo = PostRepository(FakeAPIService())

    @Test
    fun `viewModel returns success state with fake posts`() = runBlocking {
        val viewModel = PostViewModel(fakeRepo)

        // Give the coroutine time to finish
        delay(50) // tiny delay

        val state = viewModel.state.value
        assertTrue(state is PostUiState.Success)
    }
}
