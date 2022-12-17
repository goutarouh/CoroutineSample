package com.github.goutarouh.coroutinesample.data.room.memo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.goutarouh.coroutinesample.data.Memo
import java.util.UUID

@Entity
data class MemoEntity(
    @PrimaryKey(autoGenerate = false) val memoId: String = UUID.randomUUID().toString(),
    val title: String = "",
    val contents: String = ""
) {
    fun toMemo(): Memo {
        return Memo(
            memoId = memoId,
            title = title,
            contents = contents
        )
    }
}