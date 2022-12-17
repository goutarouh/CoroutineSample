package com.github.goutarouh.coroutinesample.momolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.goutarouh.coroutinesample.data.Memo

@Composable
fun MemoListScreen(
    onClickMemo: (String) -> Unit,
    onClickMemoAddButton: () -> Unit,
    viewModel: MemoListViewModel = hiltViewModel()
) {

    val state = viewModel.memoListState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val memoListState = state.value) {
            is MemoListState.Loading -> {
                Loading(modifier = Modifier.align(Alignment.Center))
            }
            is MemoListState.Error -> {}
            is MemoListState.Success -> {
                Success(
                    memoList = memoListState.memoList,
                    onClickMemo = onClickMemo
                )
            }
        }
        MemoAddButton(
            onClick = onClickMemoAddButton,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 24.dp)
        )
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier)
}

@Composable
private fun Success(
    memoList: List<Memo>,
    modifier: Modifier = Modifier,
    onClickMemo: (String) -> Unit,
) {
    if (memoList.isEmpty()) {
        MemoListEmptyContents(
            modifier = modifier
        )
    } else {
        MemoListContents(
            memoList = memoList,
            onClickMemo = onClickMemo,
            modifier = modifier
        )
    }
}

@Composable
private fun MemoListEmptyContents(
    modifier: Modifier
) {
    Text(
        text = "メモがありません",
        modifier = modifier
    )
}

@Composable
private fun MemoListContents(
    memoList: List<Memo>,
    onClickMemo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        memoList.forEach {
            MemoCard(memo = it, onClickMemo = onClickMemo)
        }
    }
}

@Composable
private fun MemoCard(
    memo: Memo,
    onClickMemo: (String) -> Unit
) {
    Text(
        text = "MemoCard${memo.memoId}",
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClickMemo(memo.memoId) }
    )
}

@Composable
private fun MemoAddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null
        )
    }
}