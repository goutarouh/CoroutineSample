package com.github.goutarouh.coroutinesample.data

import com.github.goutarouh.coroutinesample.data.room.memo.MemoEntity
import java.time.LocalDateTime

data class Memo(
    val memoId: String = "",
    val title: String,
    val contents: String = ""
) {

    fun toMemoEntity(): MemoEntity {
        return MemoEntity(
            title = title,
            contents = contents
        )
    }

    companion object {
        fun create(): Memo {
            return Memo(
                title = "これはメモです ${LocalDateTime.now()}",
                contents = "これはメモのコンテンツです。これはメモのコンテンツです。"
            )
        }
    }
}
