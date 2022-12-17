package com.github.goutarouh.coroutinesample.memodetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.coroutinesample.data.Memo
import com.github.goutarouh.coroutinesample.data.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val memoRepository: MemoRepository
): ViewModel() {

    private val _memoDetailState = MutableStateFlow<MemoDetailState>(MemoDetailState.Loading)
    val memoDetailState = _memoDetailState.asStateFlow()

    init {
        val memoId = savedStateHandle.get<String>("memoId")!!
        updateMemoDetailState(memoId)
    }

    fun updateMemoDetailState(memoId: String) {
        viewModelScope.launch {
            _memoDetailState.emit(MemoDetailState.Loading)
            val result = memoRepository.getMemo(memoId)
            val memo = result.getOrNull()
            if (memo != null) {
                _memoDetailState.emit(MemoDetailState.Success(memo))
            } else {
                _memoDetailState.emit(MemoDetailState.Error(result.exceptionOrNull()))
            }
        }
    }
}

sealed interface MemoDetailState {
    object Loading: MemoDetailState
    data class Error(
        private val _error: Throwable?
    ): MemoDetailState {
        val error: Throwable = _error ?: IllegalStateException()
    }
    data class Success(
        val memo: Memo
    ): MemoDetailState
}