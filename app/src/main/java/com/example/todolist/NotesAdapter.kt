package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notes = arrayListOf<Note>()

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent,
            false
        )
        return NotesViewHolder(view)
    }

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNote: TextView = itemView.findViewById(R.id.textViewNote)

        fun bind(note: Note) {
            textViewNote.text = note.text

            val colorResId = when (note.priority) {
                0 -> R.color.OldRose
                1 -> R.color.olivine
                else -> R.color.BurntSienna
            }

            val color = ContextCompat.getColor(itemView.context, colorResId)
            textViewNote.setBackgroundColor(color)
        }
    }
}

//class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
//
//    private val notes = arrayListOf<Note>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
//        val view: View = LayoutInflater.from(parent.context).inflate(
//            /* resource = */ R.layout.note_item,
//            /* root = */ parent,
//            /* attachToRoot = */ false
//        )
//        return NotesViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: NotesViewHolder, position: Int) {
//        val note = notes[position]
//        viewHolder.bind(note)
//    }
//
//    override fun getItemCount(): Int = notes.size
//
//    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val textViewNote: TextView = itemView.findViewById(R.id.textViewNote)
//
//        fun bind(note: Note) {
//            textViewNote.text = note.text
//
//            val colorResId = when (note.priority) {
//                0 -> R.color.OldRose
//                1 -> R.color.olivine
//                else -> R.color.BurntSienna
//            }
//
//            val color = ContextCompat.getColor(itemView.context, colorResId)
//            textViewNote.setBackgroundColor(color)
//        }
//    }
//}