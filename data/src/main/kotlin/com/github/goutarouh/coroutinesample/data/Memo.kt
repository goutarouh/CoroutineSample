package com.github.goutarouh.coroutinesample.data

import com.github.goutarouh.coroutinesample.data.room.memo.MemoEntity

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
}
