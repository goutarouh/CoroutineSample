package com.github.goutarouh.coroutinesample.memoadd

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MemoAddScreen(
    onClickNavigateMemoListButton: () -> Unit,
    viewModel: MemoAddViewModel = hiltViewModel()
) {
    val state = viewModel.memoAddState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val memoAddState = state.value) {
            is MemoAddState.Editing -> {
                MemoEditing(
                    onClickAddButton = { viewModel.saveMemo() },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is MemoAddState.Loading -> {

            }
            is MemoAddState.SaveFailed -> {}
            is MemoAddState.SaveSuccess -> {
                SaveSuccess(
                    onClickNavigateMemoListButton = onClickNavigateMemoListButton,
                    onCLickMemoAddButton = {
                        viewModel.addAnotherMemo()
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun MemoEditing(
    onClickAddButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClickAddButton,
        modifier = modifier
    ) {
        Text(text = "メモを追加する")
    }
}

@Composable
private fun SaveSuccess(
    onClickNavigateMemoListButton: () -> Unit,
    onCLickMemoAddButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(onClick = onClickNavigateMemoListButton) {
            Text(text = "一覧に戻る")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onCLickMemoAddButton) {
            Text(text = "さらにメモを追加する")
        }
    }
}