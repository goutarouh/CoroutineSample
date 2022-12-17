package com.github.goutarouh.coroutinesample.data

data class Memo(
    val memoId: String,
    val title: String,
    val contents: String = ""
) {
    init {
        require(memoId.isNotEmpty())
        require(title.isNotEmpty())
    }
}
