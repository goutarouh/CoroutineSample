package com.github.goutarouh.coroutinesample.momolist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.coroutinesample.data.Memo
import com.github.goutarouh.coroutinesample.data.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val memoRepository: MemoRepository,
): ViewModel() {

    val memoListState = memoRepository.getMemos()
        .map {
            MemoListState.Success(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = MemoListState.Loading
        )
}

sealed interface MemoListState {
    object Loading: MemoListState
    data class Error(
        private val _error: Throwable?
    ): MemoListState {
        val error: Throwable = _error ?: IllegalStateException()
    }
    data class Success(
        val memoList: List<Memo>
    ): MemoListState
}