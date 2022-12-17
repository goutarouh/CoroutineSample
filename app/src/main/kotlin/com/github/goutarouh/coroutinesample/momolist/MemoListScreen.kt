package com.github.goutarouh.coroutinesample.momolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MemoListScreen(
    onClickMemo: (String) -> Unit,
    viewModel: MemoListViewModel = viewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MemoListContents() { memoId ->
            onClickMemo(memoId)
        }
    }
}

@Composable
private fun MemoListContents(
    onClickMemo: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        repeat(10) {
            MemoCard("$it") { memoId ->
                onClickMemo(memoId)
            }
        }
    }
}

@Composable
private fun MemoCard(
    id: String,
    onClickMemo: (String) -> Unit
) {
    Text(
        text = "MemoCard${id}",
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClickMemo(id) }
    )
}