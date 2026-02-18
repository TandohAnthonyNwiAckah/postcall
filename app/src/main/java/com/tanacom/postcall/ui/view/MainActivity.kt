package com.tanacom.postcall.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tanacom.postcall.data.model.Post
import com.tanacom.postcall.ui.theme.PostCallTheme
import com.tanacom.postcall.ui.viewmodel.PostUiState
import com.tanacom.postcall.ui.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postViewModel: PostViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostCallTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Posts Call",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }, colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Red
                                )
                            )
                        },

                        ) { innerPadding ->
                        PostScreen(
                            postViewModel = postViewModel, modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = Color.Red)
    }
}

@Composable
fun PostList(posts: List<Post>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        items(
            posts, key = { post -> post.id }) { post ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )


            ) {

                Column(modifier = Modifier.padding(12.dp)) {

                    Text(
                        text = post.title,
                        color = Color.Red,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                    Text(
                        text = post.body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black

                    )

                }
            }

        }

    }
}

@Composable
fun PostScreen(postViewModel: PostViewModel, modifier: Modifier = Modifier) {
    PostScreenContent(uiState = postViewModel.state.collectAsState().value, modifier = modifier)
}

@Composable
fun PostScreenContent(uiState: PostUiState, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is PostUiState.Loading -> LoadingView()
            is PostUiState.Success -> PostList(posts = uiState.posts)
            is PostUiState.Error -> ErrorView(message = uiState.message)
        }
    }
}











@Preview(showBackground = true)
@Composable
fun PostScreenSuccessPreview() {
    PostCallTheme {
        PostScreenContent(
            uiState = PostUiState.Success(
                posts = listOf(
                    Post(
                        userId = 1,
                        id = 1,
                        title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                        body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                    ), Post(
                        userId = 1,
                        id = 2,
                        title = "qui est esse",
                        body = "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
                    )
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenLoadingPreview() {
    PostCallTheme {
        PostScreenContent(uiState = PostUiState.Loading)
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenErrorPreview() {
    PostCallTheme {
        PostScreenContent(uiState = PostUiState.Error(message = "Something went wrong"))
    }
}



