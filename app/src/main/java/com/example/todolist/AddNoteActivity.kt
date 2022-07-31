package com.example.todolist

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    val TAG="aaa"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        var dbmanager=DBManager(this)

        var id=0
        if(intent.extras != null)
        {
            var bundle:Bundle = intent.extras!!
            editTextTitle.setText(bundle.getString(PASS_TITLE))
            editTextDesc.setText(bundle.getString(PASS_DESC))
            id = bundle.getInt(PASS_ID,0)
        }

        buttonSave.setOnClickListener {
            var values=ContentValues()
            values.put("Title",editTextTitle.text.toString())
            values.put("Desc",editTextDesc.text.toString())
            if(id==0)
            {

                var idIns = dbmanager.insert(values)
                if(idIns > 0 )
                    Log.d(TAG, "ذخیره شد")
                else
                    Log.d(TAG, "خطا در ذخیره سازی")
            }
            else
            {
                var selection="id=?"
                var selectionArgs = arrayOf(id.toString())
                dbmanager.updateNote(values,selection, selectionArgs)
            }


        }
    }
}