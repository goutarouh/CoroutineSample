package com.github.goutarouh.coroutinesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.goutarouh.coroutinesample.memodetail.MemoDetailScreen
import com.github.goutarouh.coroutinesample.momolist.MemoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContent {
            MaterialTheme {
                Navigation()
            }
        }
    }
}

enum class NavDest(val destId: String) {
    MEMO_LIST("main"),
    MEMO_DETAIL("memoDetail/{memoId}")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavDest.MEMO_LIST.destId) {
        composable(NavDest.MEMO_LIST.destId) {
            MemoListScreen(onClickMemo = { memoId ->
                navController.navigate(NavDest.MEMO_DETAIL.destId.replace("{memoId}", memoId))
            })
        }
        composable(NavDest.MEMO_DETAIL.destId) {
            val memoId = it.arguments?.getString("memoId")!!
            MemoDetailScreen(id = memoId)
        }
    }
}