package com.example.todolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDatabase: NoteDatabase = NoteDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    fun getNotes(): LiveData<List<Note>> {
        return noteDatabase.notesDao().getNotes()
    }

    fun remove(note: Note) {
        val disposable = noteDatabase.notesDao().remove(note.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Action {
                Log.d("MainViewModel", "subscribe")
            }, Consumer { err ->
                Log.d("MainViewModel", "error in method removeNoteRx $err")
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}