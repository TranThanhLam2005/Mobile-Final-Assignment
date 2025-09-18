package com.example.finalassignment.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalassignment.data.local.dao.NoteDao
import com.example.finalassignment.data.local.entity.LocalNoteEntity

@Database(entities = [LocalNoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "note_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
