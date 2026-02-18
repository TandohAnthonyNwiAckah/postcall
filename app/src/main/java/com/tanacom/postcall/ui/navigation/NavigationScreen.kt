package com.tanacom.postcall.ui.navigation


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tanacom.postcall.ui.view.post.PostDetailScreen
import com.tanacom.postcall.ui.view.post.PostScreen
import com.tanacom.postcall.ui.viewmodel.PostUiState
import com.tanacom.postcall.ui.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(postViewModel: PostViewModel, modifier: Modifier = Modifier) {


    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "postList", modifier = modifier
    ) {
        // Post list screen
        composable("postList") {
            PostScreen(
                postViewModel = postViewModel, navController = navController
            )
        }

        // Post detail screen
        composable(
            "postDetail/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId")
            val uiState = postViewModel.state.collectAsState().value
            if (uiState is PostUiState.Success) {
                val post = uiState.posts.find { it.id == postId }
                if (post != null) {
                    PostDetailScreen(post = post)
                }
            }

        }
    }
}
