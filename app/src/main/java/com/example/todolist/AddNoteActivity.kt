package com.example.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextNote: EditText
    private lateinit var radioButtonLow: RadioButton
    private lateinit var radioButtonMedium: RadioButton
    private lateinit var radioButtonHigh: RadioButton
    private lateinit var buttonSaveNote: Button
    private lateinit var viewModel: AddNoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        initViews()

        viewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        viewModel.shouldCloseScreen.observe(this, Observer { shouldClose ->
            if (shouldClose) {
                finish()
            }
        })
        buttonSaveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val text = editTextNote.text.toString().trim()
        val priority = getPriority()
        if (text.isEmpty()) {
            Toast.makeText(this, R.string.toast_for_empty_note, Toast.LENGTH_SHORT).show()
        } else {
            val note = Note(text, priority)
            viewModel.add(note)
        }
    }

    private fun getPriority(): Int {
        var priority: Int
        if (radioButtonLow.isChecked) {
            priority = 0
        } else if (radioButtonMedium.isChecked) {
            priority = 1
        } else {
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