package com.github.goutarouh.coroutinesample.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

interface MemoRepository {
    fun getMemos(): Flow<List<Memo>>
    suspend fun getMemo(memoId: String): Result<Memo>
}

class MemoRepositoryImpl: MemoRepository {

    override fun getMemos(): Flow<List<Memo>> {
        return flow {
            delay(2000)
            emit(MEMO_LIST)
        }
    }

    override suspend fun getMemo(memoId: String): Result<Memo> {
        val random = (0..20).random()
        return try {
            delay(2000)
            if (random < 16) {
                Result.success(MEMO_LIST.first { it.memoId == memoId })
            } else {
                throw IOException()
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: NoSuchElementException) {
            Result.failure(e)
        }
    }

}

private val MEMO_LIST = mutableListOf<Memo>().apply {
    repeat(10) {
        add(
            Memo(
                memoId = "$it",
                title = "メモ${it}",
                contents = "これはメモ${it}の内容です。"
            )
        )
    }
}