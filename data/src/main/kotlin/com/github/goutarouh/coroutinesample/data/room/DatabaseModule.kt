package com.github.goutarouh.coroutinesample.data.room

import android.content.Context
import androidx.room.Room
import com.github.goutarouh.coroutinesample.data.room.memo.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun db(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    fun memoDao(
        db: AppDatabase
    ): MemoDao {
        return db.memoDao()
    }

}