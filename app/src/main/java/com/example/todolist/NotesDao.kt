package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun remove(id: Int)
}