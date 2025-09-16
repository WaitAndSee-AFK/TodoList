package com.example.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextNote: EditText
    private lateinit var radioButtonLow: RadioButton
    private lateinit var radioButtonMedium: RadioButton
    private lateinit var radioButtonHigh: RadioButton
    private lateinit var buttonSaveNote: Button


    private val database = Database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        initViews()

        buttonSaveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val text = editTextNote.text.toString().trim()
        val priority = getPriority()
        val id = database.notes.size
        if (text.isEmpty()) {
            Toast.makeText(this, R.string.toast_for_empty_note, Toast.LENGTH_SHORT).show()
        } else {
            val note = Note(id, text, priority)
            database.add(note)

            finish()
        }
    }

    private fun getPriority() : Int {
        var priority: Int
        if(radioButtonLow.isChecked) {
            priority = 0
        } else if(radioButtonMedium.isChecked) {
            priority = 1
        } else  {
            priority = 2
        }
        return priority
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNoteActivity::class.java)
        }
    }

    private fun initViews() {
        editTextNote = findViewById(R.id.editTextNote)
        radioButtonLow = findViewById(R.id.radioButtonLow)
        radioButtonMedium = findViewById(R.id.radioButtonMedium)
        radioButtonHigh = findViewById(R.id.radioButtonHigh)
        buttonSaveNote = findViewById(R.id.buttonSaveNote)
    }
}