package com.tanacom.postcall

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tanacom.postcall.ui.theme.PostCallTheme

class MainActivity : ComponentActivity() {

    private val postViewModel: PostViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
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
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Red
                                )
                            )
                        }
                    ) { innerPadding ->

                        PostScreen(
                            postViewModel = postViewModel,
                            modifier = Modifier.padding(innerPadding)
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
fun PostList(posts: List<Post>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        items(posts) { post ->

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

    val state = postViewModel.state.value

    when (state) {
        is UiState.Loading -> LoadingView()
        is UiState.Success -> PostList(posts = state.posts)
        is UiState.Error -> ErrorView(message = state.message)
    }

}