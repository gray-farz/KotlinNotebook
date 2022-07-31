package com.example.todolist

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.NoteAdapter
import com.example.todolist.model.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NoteAdapter.ChangeNoteItem {
    private val TAG = "aaa"
    lateinit var adapter:NoteAdapter
    var listNotes= ArrayList<Note>()
    var dbmanager:DBManager ?= null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readDatabse("%")

    }

    override fun onResume() {
        super.onResume()
        readDatabse("%")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextChange(newText: String?): Boolean {
                readDatabse("%" + newText + "%")
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    @SuppressLint("Range")
    fun readDatabse(title:String)
    {
        dbmanager=DBManager(this)
        var columns= arrayOf("ID","Title","Desc")
        var selection="Title LIKE ?"
        var selectionArgs= arrayOf(title)
        var cursor= dbmanager!!.RunQuery(columns,selection,selectionArgs,
            "Title")

        listNotes.clear()
        if(cursor.moveToFirst())
        {
            //Log.d(TAG, "moveToFirst() ")
            do{
                val id=cursor.getInt(cursor.getColumnIndex("ID"))
                val title= cursor.getString(cursor.getColumnIndex("Title"))
                val desc= cursor.getString(cursor.getColumnIndex("Desc"))
                //Log.d(TAG, "readDatabse: "+title)
                listNotes.add(Note(id,title,desc))
            }while(cursor.moveToNext())
        }

        adapter= NoteAdapter(this,listNotes,this)
        rec.adapter = adapter
        rec.layoutManager=LinearLayoutManager(this)

    }

//    private fun deleteNote(
//        note: Note,
//        dbmanager: DBManager
//    ) {
//        var selection = "ID=?"
//        var selectionArgs = arrayOf(note.id.toString())
//        dbmanager.deleteNote(selection, selectionArgs)
//        readDatabse("%")
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.menuItemNew ->
            {
                Log.d(TAG, "onOptionsItemSelected: menuItemNew")
                startActivity(Intent(this,AddNoteActivity::class.java))
            }
            R.id.app_bar_search ->
            {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun deleteNote(note: Note) {
        var selection = "ID=?"
        var selectionArgs = arrayOf(note.id.toString())
        dbmanager!!.deleteNote(selection, selectionArgs)
        readDatabse("%")
    }

    override fun updtaeNote(note: Note) {
        var intent= Intent(this,AddNoteActivity::class.java)
        intent.putExtra(PASS_TITLE,note.title)
        intent.putExtra(PASS_DESC, note.desc)
        intent.putExtra(PASS_ID,note.id)
        startActivity(intent)
    }
}