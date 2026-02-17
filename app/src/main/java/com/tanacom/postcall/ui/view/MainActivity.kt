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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tanacom.postcall.data.model.Post
import com.tanacom.postcall.ui.navigation.Screen
import com.tanacom.postcall.ui.theme.PostCallTheme
import com.tanacom.postcall.ui.viewmodel.PostViewModel
import com.tanacom.postcall.ui.viewmodel.UiState

class MainActivity : ComponentActivity() {

    private val postViewModel: PostViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PostCallTheme {

                val navController = rememberNavController()

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

                    NavHost(
                        navController = navController,
                        startDestination = Screen.PostList.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable(Screen.PostList.route) {
                            PostScreen(
                                postViewModel = postViewModel, onPostClick = { postId ->
                                    navController.navigate(
                                        Screen.PostDetail.createRoute(postId)
                                    )
                                })
                        }

                        composable(
                            route = Screen.PostDetail.route, arguments = listOf(
                                navArgument("postId") {
                                    type = NavType.IntType
                                })
                        ) { backStackEntry ->

                            val postId = backStackEntry.arguments?.getInt("postId") ?: 0

                            PostDetailScreen(postId = postId)
                        }
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
fun PostList(
    posts: List<Post>,
    onPostClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        items(
            posts,
            key = { post -> post.id }
        ) { post ->

            Card(
                onClick = { onPostClick(post.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {

                    Text(
                        text = post.title,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 6.dp)
                    )

                    Text(text = post.body)
                }
            }
        }
    }
}


@Composable
fun PostScreen(
    postViewModel: PostViewModel,
    onPostClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    PostScreenContent(
        uiState = postViewModel.state.value,
        onPostClick = onPostClick,
        modifier = modifier
    )
}


@Composable
fun PostScreenContent(
    uiState: UiState,
    onPostClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is UiState.Loading -> LoadingView()
            is UiState.Success -> PostList(
                posts = uiState.posts,
                onPostClick = onPostClick
            )

            is UiState.Error -> ErrorView(message = uiState.message)
        }
    }
}


@Composable
fun PostDetailScreen(
    postId: Int,
    postViewModel: PostViewModel = viewModel()
) {
    val post = postViewModel.getPostById(postId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        if (post != null) {
            Column {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            Text(
                text = "Loading post...",
                color = Color.Gray
            )
        }
    }
}






