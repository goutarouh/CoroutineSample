package com.github.goutarouh.coroutinesample.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.goutarouh.coroutinesample.data.room.memo.MemoDao
import com.github.goutarouh.coroutinesample.data.room.memo.MemoEntity

@Database(
    entities = [MemoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun memoDao(): MemoDao
}