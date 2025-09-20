package com.example.todolist

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var buttonAddNode: FloatingActionButton
    private lateinit var notesAdapter: NotesAdapter

    private val database = Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        notesAdapter = NotesAdapter()
        recyclerViewNotes.adapter = notesAdapter
        notesAdapter.onNoteClickListener = object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
//                database.remove(note.id)
//                showNotes()
            }
        }

        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ){
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
                note.let {
                    database.remove(it.id)
                    notesAdapter.updateNotes(database.notes)
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
       notesAdapter.updateNotes(database.notes)
    }

    private fun initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        buttonAddNode = findViewById(R.id.buttonAddNode)
    }
}