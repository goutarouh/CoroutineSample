package com.github.goutarouh.coroutinesample.momolist

import androidx.lifecycle.SavedStateHandle
import com.github.goutarouh.coroutinesample.data.Memo
import com.github.goutarouh.coroutinesample.data.MemoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class MemoListViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMemoLoading() {
        val fakeMemoRepository = object : FakeMemoRepository() {
            override val memos: Flow<List<Memo>> = flow {  }
        }
        val viewModel = MemoListViewModel(SavedStateHandle(mapOf()), fakeMemoRepository)
        runTest {
            val memoListState = viewModel.memoListState.value
            Assert.assertTrue(memoListState is MemoListState.Loading)
        }
    }

    @Test
    fun getMemoSuccess() {
        val fakeMemoRepository = object: FakeMemoRepository() {
            override val memos: Flow<List<Memo>> = flow { emit(MEMO_LIST) }
        }
        val viewModel = MemoListViewModel(SavedStateHandle(mapOf()), fakeMemoRepository)
        runTest {
            val memoListState = viewModel.memoListState.first()
            Assert.assertTrue(memoListState is MemoListState.Success)
            Assert.assertEquals(MEMO_LIST, (memoListState as MemoListState.Success).memoList)
        }
    }

    @Test
    fun getMemoError() {
        val fakeMemoRepository = object: FakeMemoRepository() {
            override val memos: Flow<List<Memo>> = flow { throw IOException() }
        }
        val viewModel = MemoListViewModel(SavedStateHandle(mapOf()), fakeMemoRepository)
        runTest {
            val memoListState = viewModel.memoListState.first()
            Assert.assertTrue(memoListState is MemoListState.Error)
            Assert.assertThrows(IOException::class.java) {
                throw (memoListState as MemoListState.Error).error
            }
        }
    }
}

private val MEMO_LIST = listOf(
    Memo(title = "memo1"),
    Memo(title = "memo2"),
)

private abstract class FakeMemoRepository: MemoRepository {
    override val memos: Flow<List<Memo>>
        get() = TODO("Not yet implemented")
    override suspend fun getMemo(memoId: String): Result<Memo> {
        TODO("Not yet implemented")
    }
    override suspend fun insertMemo(memo: Memo): Result<Unit> {
        TODO("Not yet implemented")
    }
    override suspend fun deleteMemo(memoId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}