package com.github.goutarouh.coroutinesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.goutarouh.coroutinesample.memoadd.MemoAddScreen
import com.github.goutarouh.coroutinesample.memodetail.MemoDetailScreen
import com.github.goutarouh.coroutinesample.momolist.MemoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Navigation()
            }
        }
    }
}

sealed interface NavDest {
    val destId: String
    object MemoList: NavDest {
        override val destId: String = "memoList"
    }
    object MemoAdd: NavDest {
        override val destId: String = "memoAdd"
    }
    object MemoDetail: NavDest {
        override val destId: String
            get() = "memoDetail/{memoId}"
        fun toId(memoId: String) = destId.replace("{memoId}", memoId)
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavDest.MemoList.destId) {
        composable(NavDest.MemoList.destId) {
            MemoListScreen(
                onClickMemo = { memoId ->
                    navController.singleNavigate(NavDest.MemoDetail.toId(memoId))
                },
                onClickMemoAddButton = {
                    navController.singleNavigate(NavDest.MemoAdd.destId)
                }
            )
        }
        composable(NavDest.MemoAdd.destId) {
            MemoAddScreen(
                onClickNavigateMemoListButton = {
                    navController.singleNavigate(NavDest.MemoList.destId, NavDest.MemoList.destId, true)
                }
            )
        }
        composable(NavDest.MemoDetail.destId) {
            val memoId = it.arguments?.getString("memoId")!!
            MemoDetailScreen(
                memoId = memoId,
                onClickDeleteButton = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavHostController.singleNavigate(
    destId: String, popUpToDestId: String? = null, inclusive: Boolean = false
) {
    if (currentDestination?.route != destId) {
        navigate(destId) {
            if (popUpToDestId != null) {
                popUpTo(popUpToDestId) {
                    this.inclusive = inclusive
                }
            }
        }
    }
}