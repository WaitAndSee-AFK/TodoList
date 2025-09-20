    package com.example.todolist

    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "notes")
    class Note(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val text: String,
        val priority: Int
    )