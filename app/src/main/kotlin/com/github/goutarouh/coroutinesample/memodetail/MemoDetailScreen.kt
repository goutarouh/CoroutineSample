package com.github.goutarouh.coroutinesample.memodetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.goutarouh.coroutinesample.data.Memo

@Composable
fun MemoDetailScreen(
    memoId: String,
    onClickDeleteButton: () -> Unit,
    viewModel: MemoDetailViewModel = hiltViewModel()
) {

    val state = viewModel.memoDetailState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (val memoDetailState = state.value) {
            is MemoDetailState.Loading -> {
                Loading(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is MemoDetailState.Error -> {
                Error(
                    error =  memoDetailState.error,
                    onReloadClick = {
                        viewModel.updateMemoDetailState(memoId)
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is MemoDetailState.Success -> {
                Success(
                    memo = memoDetailState.memo,
                    onClickDeleteButton = {
                        viewModel.deleteMemoThenDoAction(
                            memoId = memoId,
                            action = { onClickDeleteButton() }
                        )
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
    )
}

@Composable
private fun Error(
    error: Throwable,
    onReloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "再読み込みする",
        modifier = modifier.clickable { onReloadClick() }
    )
}

@Composable
private fun Success(
    memo: Memo,
    onClickDeleteButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = memo.title
        )
        Text(
            text = memo.contents
        )
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clickable { onClickDeleteButton() }
        )
    }
}
