package br.com.hahn.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.hahn.ceep.database.AppDatabase
import br.com.hahn.ceep.databinding.ActivityNotesListBinding
import br.com.hahn.ceep.extensions.navigate
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.hahn.ceep.webclient.RetrofitInitialize
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class NotesListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotesListBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaNotasAdapter(this)
    }
    private val dao by lazy {
        AppDatabase.getInstance(this).noteDao()
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleFab()
        configRecyclerView()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getNotes()
            }
        }
        val call : Call<List<Note>> = RetrofitInitialize().service.findAll()
        lifecycleScope.launch(IO) {
            val response : Response<List<Note>> = call.execute()
            response.body()?.let { notes ->
                Log.i("Get" , "onCreate: $notes")
            }
        }
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
        dao.findAll().collect { foundNotes ->
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