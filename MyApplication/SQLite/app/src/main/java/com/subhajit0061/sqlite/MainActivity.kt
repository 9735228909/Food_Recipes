package com.subhajit0061.sqlite

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.subhajit0061.sqlite.adapter.MyAdapter
import com.subhajit0061.sqlite.database.DBHelper
import com.subhajit0061.sqlite.databinding.ActivityMainBinding
import com.subhajit0061.sqlite.model.Contacts

class MainActivity : AppCompatActivity(), OnClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var dbHelper: DBHelper
    private val list = ArrayList<Contacts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MyAdapter(list, this)
        dbHelper = DBHelper(this)

        binding.recyclerView.adapter = adapter

        readAllData()

        binding.btnAdd.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_box)

        dialog.window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)

        val edtName = dialog.findViewById<EditText>(R.id.edtName)
        val edtNumber = dialog.findViewById<EditText>(R.id.edtNumber)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)

        dialog.show()

        btnSave.setOnClickListener {

            val name = edtName.text.toString()
            val number = edtNumber.text.toString()

            insertData(name, number)

            dialog.dismiss()
        }

    }

    private fun insertData(name: String, number: String) {

        val db = dbHelper.writableDatabase

        val values = ContentValues()

        values.put(DBHelper.COLUMN_NAME, name)
        values.put(DBHelper.COLUMN_NUMBER, number)

        val res = db.insert(DBHelper.TABLE_NAME, null, values)

        if (res != -1L) {

            val cursor =
                db.rawQuery("SELECT ${DBHelper.COLUMN_ID} FROM ${DBHelper.TABLE_NAME}", null)

            cursor.moveToLast()

            val id = cursor.getInt(0)

            cursor.close()

            list.add(Contacts(id, name, number))
            adapter.notifyItemInserted(list.size - 1)

        } else {
            Toast.makeText(this, "Something wrong!", Toast.LENGTH_SHORT).show()
        }

        db.close()

    }

    private fun readAllData() {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM ${DBHelper.TABLE_NAME}", null)

        while (cursor.moveToNext()) {

            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val number = cursor.getString(2)

            list.add(Contacts(id, name, number))
        }

        cursor.close()

        db.close()

        adapter.notifyItemInserted(list.size - 1)

    }

    override fun onDelete(position: Int) {

        val db = dbHelper.writableDatabase

        val id = list[position].id

        val res = db.delete(DBHelper.TABLE_NAME,"${DBHelper.COLUMN_ID} = $id", null)

        if(res > 0){

            list.removeAt(position)
            adapter.notifyItemRemoved(position)

        }
        else{
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }

    }

}