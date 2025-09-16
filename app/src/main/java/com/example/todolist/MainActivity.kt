package com.example.todolist

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutNotes: LinearLayout
    private lateinit var buttonAddNode: FloatingActionButton

    private val database = Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        buttonAddNode.setOnClickListener {
            startActivity(AddNoteActivity.newIntent(this))
        }
    }

    override fun onResume() {
        super.onResume()
        showNotes()
    }

    private fun showNotes() {
        linearLayoutNotes.removeAllViews()
        for (note in database.notes) {
            val view: View = layoutInflater.inflate(R.layout.note_item, linearLayoutNotes, false)
            val textViewNote: TextView = view.findViewById(R.id.textViewNote)
            textViewNote.text = note.text

            val colorResId = when(note.priority) {
                0 -> R.color.OldRose
                1 -> R.color.olivine
                else -> R.color.BurntSienna
            }

            val color: Int = ContextCompat.getColor(this, colorResId)
            textViewNote.setBackgroundColor(color)
            linearLayoutNotes.addView(view)
        }
    }

    private fun initViews() {
        linearLayoutNotes = findViewById(R.id.linearLayoutNotes)
        buttonAddNode = findViewById(R.id.buttonAddNode)
    }
}