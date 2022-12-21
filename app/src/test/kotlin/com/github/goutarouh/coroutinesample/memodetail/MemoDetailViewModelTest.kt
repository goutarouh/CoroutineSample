package com.github.goutarouh.coroutinesample.memodetail

import androidx.lifecycle.SavedStateHandle
import com.github.goutarouh.coroutinesample.data.Memo
import com.github.goutarouh.coroutinesample.momolist.FakeMemoRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class MemoDetailViewModelTest {


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initSuccess() {
        val fakeMemoRepository = object: FakeMemoRepository() {
            override suspend fun getMemo(memoId: String): Result<Memo> {
                return Result.success(MEMO)
            }
        }
        val viewModel = MemoDetailViewModel(SavedStateHandle(mapOf("memoId" to "")), fakeMemoRepository)
        runTest {
            Assert.assertTrue(viewModel.memoDetailState.first() is MemoDetailState.Success)
        }
    }

    @Test
    fun initError() {
        val fakeMemoDetailState = object: FakeMemoRepository() {
            override suspend fun getMemo(memoId: String): Result<Memo> {
                return Result.failure(IOException())
            }
        }
        val viewModel = MemoDetailViewModel(SavedStateHandle(mapOf("memoId" to "")), fakeMemoDetailState)
        runTest {
            val memoDetailState = viewModel.memoDetailState.first()
            Assert.assertTrue(memoDetailState is MemoDetailState.Error)
        }
    }

    @Test
    fun initErrorAndRetrySuccess() {
        val fakeMemoRepository = object: FakeMemoRepository() {
            var num = 0
            override suspend fun getMemo(memoId: String): Result<Memo> {
                if (num == 0) {
                    num += 1
                    return Result.failure(IOException())
                }
                return Result.success(MEMO)
            }
        }
        val viewModel = MemoDetailViewModel(SavedStateHandle(mapOf("memoId" to "")), fakeMemoRepository)
        runTest {
            val memoDetailStateError = viewModel.memoDetailState.first()
            Assert.assertTrue(memoDetailStateError is MemoDetailState.Error)

            viewModel.updateMemoDetailState("")
            val memoDetailStateSuccess = viewModel.memoDetailState.first()
            Assert.assertTrue(memoDetailStateSuccess is MemoDetailState.Success)
        }
    }
}

private val MEMO = Memo(title = "memo")