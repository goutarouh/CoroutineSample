package com.github.goutarouh.coroutinesample.app

import com.github.goutarouh.coroutinesample.data.MemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun memoRepository(): MemoRepository {
        return MemoRepository()
    }

}