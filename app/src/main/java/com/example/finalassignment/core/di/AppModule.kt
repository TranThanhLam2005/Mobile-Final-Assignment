package com.example.finalassignment.core.di

import android.content.Context
import androidx.room.Room
import com.example.finalassignment.data.local.dao.NoteDao
import com.example.finalassignment.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao {
        return db.noteDao()
    }
}