package com.example.todolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val noteDatabase = NoteDatabase.getInstance(application)
    private val _shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val shouldCloseScreen: LiveData<Boolean>
        get() = _shouldCloseScreen

    fun add(note: Note) {
        val disposable: Disposable = noteDatabase.notesDao().add(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Action {
                _shouldCloseScreen.value = true
            }, Consumer { err ->
                Log.d("MainViewModel", "error in method addNoteRx $err")
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}