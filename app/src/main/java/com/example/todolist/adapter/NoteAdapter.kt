package com.example.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.Note

class NoteAdapter(var context: Context,var listNotes:List<Note>,
    var changeNoteItem: ChangeNoteItem):
    RecyclerView.Adapter<NoteAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view=LayoutInflater.from(context).inflate(R.layout.note_layout,
        parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindNote(listNotes[position])
        holder.imgEdit.setOnClickListener {
            changeNoteItem.updtaeNote(listNotes[position])
        }
        holder.imgDel.setOnClickListener {
            changeNoteItem.deleteNote(listNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return listNotes.count()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val txtTitle:TextView = itemView.findViewById(R.id.textViewTitle)
        val txtDesc:TextView = itemView.findViewById(R.id.textViewDescription)
        val imgDel:ImageView = itemView.findViewById(R.id.imageViewDelete)
        val imgEdit:ImageView=itemView.findViewById(R.id.imageViewEdit)
        fun bindNote(note:Note)
        {
            txtTitle.text=note.title
            txtDesc.text=note.desc
//            imgDel.setOnClickListener {
//                itemClick(note)
//            }
        }
    }

    interface ChangeNoteItem
    {
        fun deleteNote(note:Note)
        fun updtaeNote(note:Note)
    }
}