package br.com.hahn.ceep.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.hahn.ceep.R
import br.com.hahn.ceep.database.AppDatabase
import br.com.hahn.ceep.databinding.ActivityFormNoteBinding
import br.com.hahn.ceep.extensions.tryLoadImage
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.repository.NoteRepository
import br.com.hahn.ceep.ui.dialog.FormImageDialog
import br.com.hahn.ceep.webclient.NoteWebClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FormNoteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormNoteBinding.inflate(layoutInflater)
    }
    private var image : MutableStateFlow<String?> = MutableStateFlow(null)
    private  val repository by lazy {
        NoteRepository(
            AppDatabase.getInstance(this).noteDao(),
            NoteWebClient()
        )
    }
    private var noteId : String? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.activityFormNoteToolbar)
        changeImage()
        tryLoadIdNote()
        lifecycleScope.launch {
            launch {
                getNote()
            }
            launch {
                handleImageUpload()
            }
        }
    }

    private suspend fun handleImageUpload() {
        val imageNote = binding.activityFormNoteImage
        image.collect { newImage ->
            imageNote.visibility = if (newImage.isNullOrBlank()) GONE
            else {
                imageNote.tryLoadImage(newImage)
                VISIBLE
            }
        }
    }

    private suspend fun getNote() {
        noteId?.let { id ->
             repository.findById(id).filterNotNull().collect { foundNote ->
                noteId = foundNote.id
                image.value = foundNote.image
                binding.activityFormNoteTitle.setText(foundNote.title)
                binding.activityFormNoteDescription.setText(foundNote.description)
            }
        }
    }

    private fun tryLoadIdNote() {
        noteId = intent.getStringExtra(NOTE_ID)
    }

    private fun changeImage() {
        binding.activityFormNoteAddImage.setOnClickListener {
            FormImageDialog(this).showOff(image.value) { uploadedImage ->
                binding.activityFormNoteImage.tryLoadImage(uploadedImage)
                image.value = uploadedImage
            }
        }
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.form_note_menu , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.form_note_menu_save -> {
                add()
            }

            R.id.form_note_menu_remove -> {
                remove()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun remove() {
        lifecycleScope.launch {
            noteId?.let { id ->
                repository.remove(id)
            }
            finish()
        }
    }

    private fun add() {
        val note = createNote()
        lifecycleScope.launch {
            repository.save(note)
            finish()
        }
    }

    private fun createNote() : Note {
        val title = binding.activityFormNoteTitle.text.toString()
        val description = binding.activityFormNoteDescription.text.toString()
        return noteId?.let { id ->
            Note(
                id = id , title = title , description = description , image = image.value
            )
        } ?: Note(
            title = title , description = description , image = image.value
        )
    }
}
