    package com.example.todolist

    import kotlin.random.Random


    object Database {

//        private lateinit var instance: Database
////
////        fun getInstance(): Database {
////            if (!::instance.isInitialized) {
////                instance = Database
////            }
////            return instance
////        }

        private val _notes = arrayListOf<Note>()
        val notes
            get() = _notes.toList()

        init {
            for (i in 0..20) {
                val note = Note(i, "Note $i", Random.nextInt(3))
                _notes.add(note)
            }
        }

        fun add(note: Note) {
            _notes.add(note)
        }

        fun remove(id: Int): Boolean {
            return try {
                _notes.removeIf { it.id == id }
            } catch (e: Exception) {
                false
            }
        }
    }