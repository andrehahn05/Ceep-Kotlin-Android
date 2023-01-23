package br.com.hahn.ceep.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.hahn.ceep.databinding.NoteItemBinding
import br.com.hahn.ceep.extensions.tryLoadImage
import br.com.hahn.ceep.model.Note

class ListaNotasAdapter(
    private val context : Context ,
    var handleItemClicked : (note : Note) -> Unit = {} ,
    notes : List<Note> = emptyList() ,
) : RecyclerView.Adapter<ListaNotasAdapter.ViewHolder>() {

    private val notes = notes.toMutableList()

    class ViewHolder(
        private val binding : NoteItemBinding ,
        private val handleClickOnItem : (note : Note) -> Unit ,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var note : Note

        init {
            itemView.setOnClickListener {
                if (::note.isInitialized) {
                    handleClickOnItem(note)
                }
            }
        }

        fun bind(note : Note) {
            this.note = note
            binding.noteItemTitle.text = note.title
            binding.noteItemDescription.text = note.description
            val imageNote = binding.noteItemImage
            imageNote.visibility = if (note.image.isNullOrBlank()) {
                GONE
            } else {
                imageNote.tryLoadImage(note.image)
                VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(
        parent : ViewGroup ,
        viewType : Int ,
    ) : ViewHolder =
            ViewHolder(NoteItemBinding.inflate(LayoutInflater.from(context)) , handleItemClicked)

    override fun onBindViewHolder(
        holder : ViewHolder ,
        position : Int ,
    ) {
        holder.bind(notes[position])
    }

    override fun getItemCount() : Int = notes.size

    fun toUpdate(notes : List<Note>) {
        notifyItemRangeRemoved(0 , this.notes.size)
        this.notes.clear()
        this.notes.addAll(notes)
        notifyItemInserted(this.notes.size)
    }
}
