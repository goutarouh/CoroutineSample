package com.github.goutarouh.coroutinesample.data

import kotlinx.coroutines.delay
import java.io.IOException

class MemoRepository {

    suspend fun getMemo(memoId: String): Result<Memo> {
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