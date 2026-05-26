package com.example.sqlitedbapp

import android.app.Activity
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : Activity(), View.OnClickListener {

    lateinit var etStdntID: EditText
    lateinit var etStdntName: EditText
    lateinit var etStdntProg: EditText
    lateinit var btAdd: Button
    lateinit var btDelete: Button
    lateinit var btSearch: Button
    lateinit var btView: Button
    lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etStdntID = findViewById(R.id.etStdntID)
        etStdntName = findViewById(R.id.etStdntName)
        etStdntProg = findViewById(R.id.etStdntProg)

        btAdd = findViewById(R.id.btAdd)
        btDelete = findViewById(R.id.btDelete)
        btSearch = findViewById(R.id.btSearch)
        btView = findViewById(R.id.btView)

        btAdd.setOnClickListener(this)
        btDelete.setOnClickListener(this)
        btSearch.setOnClickListener(this)
        btView.setOnClickListener(this)

        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS student (stdnt_id VARCHAR, stdnt_name VARCHAR, stdnt_prog VARCHAR);")
    }

    fun clearText() {
        etStdntID.setText("")
        etStdntName.setText("")
        etStdntProg.setText("")
        etStdntID.requestFocus()
    }

    fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

    override fun onClick(view: View) {
        if (view == btAdd) {
            db.execSQL("INSERT INTO student VALUES ('" + etStdntID.text + "','" + etStdntName.text + "','" + etStdntProg.text + "');")
            showMessage("Success", "Record added.")
            clearText()
        } else if (view == btDelete) {
            val c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='" + etStdntID.text + "'", null)
            if (c.moveToFirst()) {
                db.execSQL("DELETE FROM student WHERE stdnt_id='" + etStdntID.text + "'")
                showMessage("Success", "Record Deleted.")
                clearText()
            }
            c.close()
        } else if (view == btSearch) {
            val c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='" + etStdntID.text + "'", null)
            val buffer = StringBuffer()
            if (c.moveToFirst()) {
                buffer.append("Name: " + c.getString(1) + "\n")
                buffer.append("Program: " + c.getString(2) + "\n\n")
            }
            showMessage("Student Details", buffer.toString())
            c.close()
        } else if (view == btView) {
            val c = db.rawQuery("SELECT * FROM student", null)
            if (c.count == 0) {
                showMessage("Error", "No records found.")
                c.close()
                return
            }
            val buffer = StringBuffer()
            while (c.moveToNext()) {
                buffer.append("ID: " + c.getString(0) + "\n")
                buffer.append("Name: " + c.getString(1) + "\n")
                buffer.append("Program: " + c.getString(2) + "\n\n")
            }
            showMessage("Student Details", buffer.toString())
            c.close()
        }
    }
}
