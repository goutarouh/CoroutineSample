package com.github.goutarouh.coroutinesample.data

import com.github.goutarouh.coroutinesample.data.room.memo.MemoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.CoroutineContext

interface MemoRepository {
    fun getMemos(): Flow<List<Memo>>
    suspend fun getMemo(memoId: String): Result<Memo>
    suspend fun insertMemo(memo: Memo): Result<Unit>
    suspend fun deleteMemo(memoId: String): Result<Unit>
}

class MemoRepositoryImpl(
    private val memoDao: MemoDao,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
): MemoRepository {

    override fun getMemos(): Flow<List<Memo>> {
        return memoDao.getMemoList().map { memoEntityList ->
            memoEntityList.map { it.toMemo() }
        }
    }

    override suspend fun getMemo(memoId: String): Result<Memo> = withContext(coroutineContext) {
        return@withContext try {
            val memo = memoDao.getMemo(memoId).toMemo()
            Result.success(memo)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun insertMemo(memo: Memo): Result<Unit> = withContext(coroutineContext) {
        return@withContext try {
            memoDao.insertMemo(memo.toMemoEntity())
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMemo(memoId: String): Result<Unit> = withContext(coroutineContext) {
        return@withContext try {
            memoDao.deleteMemo(memoId)
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}