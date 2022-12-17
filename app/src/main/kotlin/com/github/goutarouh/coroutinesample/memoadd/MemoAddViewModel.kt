package com.github.goutarouh.coroutinesample.memoadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.coroutinesample.data.Memo
import com.github.goutarouh.coroutinesample.data.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MemoAddViewModel @Inject constructor(
    private val memoRepository: MemoRepository
): ViewModel() {

    private val _memoAddState = MutableStateFlow<MemoAddState>(MemoAddState.Editing)
    val memoAddState = _memoAddState.asStateFlow()

    fun saveMemo() {
        viewModelScope.launch {
            _memoAddState.emit(MemoAddState.Loading)
            val result = memoRepository.insertMemo(MEMO)
            if (result.isSuccess) {
                _memoAddState.emit(MemoAddState.SaveSuccess)
            } else {
                _memoAddState.emit(MemoAddState.SaveFailed(result.exceptionOrNull()))
            }
        }
    }

    fun addAnotherMemo() {
        viewModelScope.launch {
            _memoAddState.emit(MemoAddState.Editing)
        }
    }

}


sealed interface MemoAddState {
    object Editing: MemoAddState
    object Loading: MemoAddState
    data class SaveFailed(
        private val _error: Throwable?
    ): MemoAddState {
        val error: Throwable = _error ?: IllegalStateException()
    }
    object SaveSuccess: MemoAddState
}

private val MEMO = Memo(
    title = "これはメモです ${LocalDateTime.now()}",
    contents = "これはメモのコンテンツです。これはメモのコンテンツです。"
)