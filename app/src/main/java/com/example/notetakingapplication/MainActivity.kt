package com.example.notetakingapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesAdapter {

    private lateinit var viewModel: NoteViewModel
    private lateinit var etNote: TextView
    private lateinit var submit: Button
    private lateinit var rvNotes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNote = findViewById(R.id.etNote)
        submit = findViewById(R.id.addNote)
        rvNotes = findViewById(R.id.rvNotes)

        rvNotes.layoutManager = LinearLayoutManager(this)
        val adapter = NotesAdapter(this,this)
        rvNotes.adapter = adapter

        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        submit.setOnClickListener{
            if (etNote.text.isNotEmpty()) {
                val note = Note(etNote.text.toString())
                viewModel.insertNote(note)
            }else{
                Toast.makeText(this,"Enter The Note",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.allNote.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
    }
}