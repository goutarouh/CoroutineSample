package com.github.goutarouh.coroutinesample.data.room.memo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {

    @Query("SELECT * FROM MemoEntity")
    fun getMemoList(): Flow<List<MemoEntity>>

    @Query("SELECT * FROM MemoEntity where memoId = :memoId")
    suspend fun getMemo(memoId: String): MemoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memoDao: MemoEntity)

}