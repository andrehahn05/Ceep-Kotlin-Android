package br.com.hahn.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.hahn.ceep.database.AppDatabase
import br.com.hahn.ceep.databinding.ActivityNotesListBinding
import br.com.hahn.ceep.extensions.navigate
import br.com.hahn.ceep.repository.NoteRepository
import br.com.hahn.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.hahn.ceep.webclient.NoteWebClient
import kotlinx.coroutines.launch

class NotesListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotesListBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaNotasAdapter(this)
    }
    private val repository by lazy {
        NoteRepository(
            AppDatabase.getInstance(this).noteDao(),
            NoteWebClient()
        )
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleFab()
        configRecyclerView()
        lifecycleScope.launch {
            launch {
                synchronize()
            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getNotes()
            }
        }
    }

    private suspend fun synchronize() {
        repository.synchronize()
    }

    private fun handleFab() {
        binding.activityListNotesFab.setOnClickListener {
            Intent(this , FormNoteActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun configRecyclerView() {
        binding.activityListNotesRecyclerview.adapter = adapter
        adapter.handleItemClicked = { note ->
            navigate(FormNoteActivity::class.java) {
                putExtra(NOTE_ID , note.id)
            }
        }
    }

    private suspend fun getNotes() {
        repository.findAll()
            .collect { foundNotes ->
                binding.activityListOfEmptyNotes.visibility = if (foundNotes.isEmpty()) {
                    binding.activityListNotesRecyclerview.visibility = GONE
                    VISIBLE
                } else {
                    binding.activityListNotesRecyclerview.visibility = VISIBLE
                    adapter.toUpdate(foundNotes)
                    GONE
                }
            }
    }
}