package com.tanacom.postcall.ui.navigation


sealed class Screen(val route: String) {

    object PostList : Screen("post_list")

    object PostDetail : Screen("post_detail/{postId}") {
        fun createRoute(postId: Int): String {
            return "post_detail/$postId"
        }
    }
}
