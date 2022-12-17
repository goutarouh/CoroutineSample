package com.github.goutarouh.coroutinesample.app

import com.github.goutarouh.coroutinesample.data.MemoRepository
import com.github.goutarouh.coroutinesample.data.MemoRepositoryImpl
import com.github.goutarouh.coroutinesample.data.room.memo.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun memoRepository(
        memoDao: MemoDao
    ): MemoRepository {
        return MemoRepositoryImpl(
            memoDao = memoDao
        )
    }

}