package com.example.finalassignment.data.local.dao

import androidx.room.*
import com.example.finalassignment.data.local.entity.LocalNoteEntity

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: LocalNoteEntity): Long

    @Update
    suspend fun updateNote(note: LocalNoteEntity)

    @Delete
    suspend fun deleteNote(note: LocalNoteEntity)

    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    suspend fun getAllNotes(): List<LocalNoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Int): LocalNoteEntity?

    @Query("SELECT * FROM notes WHERE timestamp > :after")
    suspend fun getNotesAfter(after: Long): List<LocalNoteEntity>
}