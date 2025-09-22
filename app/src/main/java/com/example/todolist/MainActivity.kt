package com.example.todolist

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.logging.Handler
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var buttonAddNode: FloatingActionButton
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var noteDatabase: NoteDatabase

    //    private val dispatcher = Executors.newFixedThreadPool(
//        Runtime.getRuntime().availableProcessors()
//    ).asCoroutineDispatcher()
    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutine.launch {
            noteDatabase = NoteDatabase.getInstance(application)
        }
        initViews()

        notesAdapter = NotesAdapter()
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        recyclerViewNotes.adapter = notesAdapter
        notesAdapter.onNoteClickListener = object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
//                database.remove(note.id)
//                showNotes()
            }
        }

        val itemTouchHelper: ItemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val note = notesAdapter.notes.get(position)
                    coroutine.launch {
                        noteDatabase.notesDao().remove(note.id)
                        withContext(Dispatchers.Main) {
                            showNotes()
                        }

                    }
                }
            })
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes)

        buttonAddNode.setOnClickListener {
            startActivity(AddNoteActivity.newIntent(this))
        }
    }

    override fun onResume() {
        super.onResume()
        showNotes()
    }

    private fun showNotes() {
        try {
            coroutine.launch {
                notesAdapter.updateNotes(noteDatabase.notesDao().getNotes())
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Error loading notes", e)
        }
    }

    private fun initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        buttonAddNode = findViewById(R.id.buttonAddNode)
    }
}