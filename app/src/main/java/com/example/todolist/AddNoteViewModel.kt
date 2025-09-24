package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDatabase = NoteDatabase.getInstance(application)
    private val _shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val shouldCloseScreen: LiveData<Boolean>
        get() = _shouldCloseScreen


    fun add(note: Note) {
        viewModelScope.launch {
            noteDatabase.notesDao().add(note)
            _shouldCloseScreen.postValue(true)
        }
    }
}