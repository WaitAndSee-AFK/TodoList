package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDatabase: NoteDatabase = NoteDatabase.getInstance(application)

    fun getNotes(): LiveData<List<Note>> {
        return noteDatabase.notesDao().getNotes()
    }

    fun remove(note: Note) {
        viewModelScope.launch {
            noteDatabase.notesDao().remove(note.id)
        }
    }
}