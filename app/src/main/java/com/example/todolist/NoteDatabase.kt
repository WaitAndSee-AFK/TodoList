package com.example.todolist

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        private var instance: NoteDatabase? = null
        private val lock = Any()
        private const val DB_NAME = "notes.db"

        fun getInstance(application: Application): NoteDatabase {
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }
                return Room.databaseBuilder(
                    application,
                    NoteDatabase::class.java,
                    DB_NAME
                ).build()
            }
        }
    }

    abstract fun notesDao(): NotesDao
}